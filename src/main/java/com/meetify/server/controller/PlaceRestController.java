package com.meetify.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meetify.server.model.Id;
import com.meetify.server.model.Place;
import com.meetify.server.model.googleplace.GooglePlace;
import com.meetify.server.model.googleplace.Result;
import com.meetify.server.repository.PlaceRepository;
import com.meetify.server.repository.UserRepository;
import com.meetify.server.utils.HttpRequest;
import com.meetify.server.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Created by dmitry on 10/14/16.
 */

@RestController
@RequestMapping("/place")
public class PlaceRestController {
    private final UserRepository userRepository;
    private final PlaceRepository placeRepository;
    
    @Autowired
    public PlaceRestController(UserRepository userRepository, PlaceRepository placeRepository, @SuppressWarnings("SpringJavaAutowiringInspection") EntityManager entityManager) {
        this.userRepository = userRepository;
        this.placeRepository = placeRepository;
        //noinspection JpaQlInspection
        Object object = entityManager.createQuery("select max(place.id) from Place as place")
                .getResultList().get(0);
        Id id;
        if (object == null) {
            id = new Id((long) 0);
        } else {
            id = (Id) object;
        }
        Place.setCurrentId(id.getId() + 1);
    }
    
    @SuppressWarnings("ResultOfMethodCallIgnored")
    @RequestMapping("/getNearby")
    public ResponseEntity<?> getNearby(@RequestParam(value = "lat", defaultValue = "48.468488") String lat,
                                       @RequestParam(value = "lon", defaultValue = "35.049684") String lon,
                                       @RequestParam(value = "radius", defaultValue = "100") String radius) throws IOException {
        try {
            Double.parseDouble(lat);
            Double.parseDouble(lon);
            Double.parseDouble(radius);
        } catch (Exception e) {
            return new ResponseEntity<>(null, null, HttpStatus.FORBIDDEN);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        GooglePlace googlePlace = objectMapper.readValue(StringUtils.makeString(HttpRequest.request(lat, lon, radius)), GooglePlace.class);
        googlePlace.setResults(sort(googlePlace.getResults()));
        googlePlace.getResults().forEach(result ->
                result.getPhotos().forEach(photo ->
                        photo.setPhotoReference(HttpRequest.photoRefToUrl(photo.getPhotoReference()))));
        return new ResponseEntity<>(googlePlace, null, HttpStatus.OK);
    }
    
    private List<Result> sort(List<Result> results) {
        List<Result> resultsChanged = new ArrayList<>();
        Optional.of(results).ifPresent(results1 -> results1.stream()
                .filter(result -> result.getPhotos() != null && result.getPhotos().size() > 0)
                .forEach(resultsChanged::add));
        return resultsChanged;
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> get(@RequestParam(value = "id", defaultValue = "") String id) {
        return new ResponseEntity<>(placeRepository.findById(new Id(Long.parseLong(id))).orElse(null),
                null, HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> post(@RequestParam(value = "name") String name,
                                  @RequestParam(value = "owner") String owner,
                                  @RequestParam(value = "allowed") String allowed) {
        Id id = new Id(Long.parseLong(owner));
        StringBuilder response = new StringBuilder();
        userRepository.findById(id).ifPresent(user1 -> {
            Place place = new Place(name, user1);
            if (!allowed.equals("")) {
                Arrays.stream(allowed.split(",")).forEach(s ->
                        userRepository.findById(new Id(Long.parseLong(s))).ifPresent(user -> {
                            user.getAllowed().add(place.getId());
                            place.getAllowed().add(user.getId());
                            userRepository.save(user);
                        })
                );
            }
            user1.getCreated().add(place);
            userRepository.save(user1);
            placeRepository.save(place);
            response.append(place.toString());
        });
        return new ResponseEntity<>(response.toString(), null, HttpStatus.OK);
    }
    
}
