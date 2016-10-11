package com.meetify.server.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.meetify.server.model.Key;
import com.meetify.server.model.Place;
import com.meetify.server.model.User;
import com.meetify.server.model.embeddable.Location;
import com.meetify.server.repository.KeyRepository;
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

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

/**
 * com.meetify.server.controller
 * Created by kr3v on 08.10.2016.
 */
@RestController
@RequestMapping("/place")
public class PlaceRestController {
    
    // TODO: 11.10.2016 test all shit
    private final PlaceRepository placeRepository;
    private final KeyRepository keyRepository;
    private final UserRepository userRepository;
    
    @Autowired
    public PlaceRestController(UserRepository userRepository, PlaceRepository placeRepository, KeyRepository keyRepository) {
        this.userRepository = userRepository;
        this.placeRepository = placeRepository;
        this.keyRepository = keyRepository;
    }
    
    @SuppressWarnings("ResultOfMethodCallIgnored")
    @RequestMapping("/getNearby")
    public ResponseEntity<?> getNearby(@RequestParam(value = "lat", defaultValue = "48.468488") String lat,
                                       @RequestParam(value = "lon", defaultValue = "35.049684") String lon,
                                       @RequestParam(value = "radius", defaultValue = "20") String radius) throws IOException {
        try {
            Double.parseDouble(lat);
            Double.parseDouble(lon);
            Double.parseDouble(radius);
        } catch (Exception e) {
            return new ResponseEntity<>(null, null, HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(StringUtils.makeString(HttpRequest.request(lat, lon, radius)), null, HttpStatus.OK);
    }
    
    private User auth(String vkID, String devInfo) {
        Optional<User> u = userRepository.findByVkID(Long.parseLong(vkID));
        Optional<Key> k = keyRepository.findByDevInfo(devInfo);
        if (u.isPresent() && (k.isPresent() && k.get().usable())) {
            return u.get();
        } else {
            return null;
        }
    }
    
    private ResponseEntity<Place> createPlace(String lat, String lon, String name, String desc, String photoRef,
                                              User user, String involved) throws JsonProcessingException {
        if (user == null) {
            return new ResponseEntity<>(null, null, HttpStatus.FORBIDDEN);
        }
        User temp = new User(null, null);
        Set<Long> involvedUsers = StringUtils.setFromString(involved);
        Place place = new Place(name, desc, photoRef, user, involvedUsers,
                new Location(Double.parseDouble(lat), Double.parseDouble(lon)));
        //TODO: 11.10.2016 read about custom-find to try to improve speed
        involvedUsers.forEach(aLong ->
                userRepository.findByVkID(aLong).ifPresent(user1 ->
                        userRepository.save(user1.addPlacesInvolved(place.getId()))));
        System.out.println(place);
        user.getPlacesCreated().add(place);
        System.out.println(user + "\n" + place);
        placeRepository.save(place);
        userRepository.save(user);
        return new ResponseEntity<>(place, null, HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> post(@RequestParam(value = "lat") String lat,
                                  @RequestParam(value = "lon") String lon,
                                  @RequestParam(value = "name") String name,
                                  @RequestParam(value = "desc", defaultValue = "") String desc,
                                  @RequestParam(value = "photo_ref", defaultValue = "") String photoRef,
                                  @RequestParam(value = "creator") String creatorId,
                                  @RequestParam(value = "involved", defaultValue = "") String involved,
                                  @RequestParam(value = "key") String key) throws InstantiationException, IllegalAccessException, JsonProcessingException {
//        User user = auth(creatorId, key);
//        if (user == null) {
//            return new ResponseEntity<>(null, null, HttpStatus.FORBIDDEN);
//        }
        System.out.println(Long.parseLong(creatorId));
        User user = userRepository.findByVkID(Long.parseLong(creatorId)).get();
        System.out.println(user);
        return createPlace(lat, lon, name, desc, photoRef, user, involved);
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<?> put(@RequestParam(value = "id", defaultValue = "") String id,
                                 @RequestParam(value = "lat") String lat,
                                 @RequestParam(value = "lon") String lon,
                                 @RequestParam(value = "name") String name,
                                 @RequestParam(value = "desc", defaultValue = "") String desc,
                                 @RequestParam(value = "photo_ref", defaultValue = "") String photoRef,
                                 @RequestParam(value = "creator") String creatorId,
                                 @RequestParam(value = "involved", defaultValue = "") String involved,
                                 @RequestParam(value = "key") String key) throws IllegalAccessException, InstantiationException, JsonProcessingException {
//        User user = auth(creatorId, key);
//        if (user == null) {
//            return new ResponseEntity<>(null, null, HttpStatus.FORBIDDEN);
//        }
        User user = userRepository.findByVkID(Long.parseLong(creatorId)).get();
        if (id.equals("")) {
            return post(lat, lon, name, desc, photoRef, creatorId, involved, key);
        } else {
            ResponseEntity<Place> responseEntity = createPlace(lat, lon, name, desc, photoRef, user, involved);
            Optional<Place> p = placeRepository.findById(Long.parseLong(id));
            if (!p.isPresent()) {
                return new ResponseEntity<>(null, null, HttpStatus.FORBIDDEN);
            } else {
                Place place = p.get().update(responseEntity.getBody());
                placeRepository.save(place);
                return new ResponseEntity<>(place, null, HttpStatus.OK);
            }
            
        }
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> get(@RequestParam(value = "id") String id,
                                 @RequestParam(value = "key") String key) {
//        User user = auth(creatorId, key);
//        if (user == null) {
//            return new ResponseEntity<>(null, null, HttpStatus.FORBIDDEN);
//        }
        User user = userRepository.findByVkID(Long.parseLong(key)).get();
        System.out.println(user);
        Optional<Place> p = placeRepository.findById(Long.parseLong(id));
        if ((user != null && p.isPresent()) && (user.equals(p.get().getCreator()))) {// || p.get().getAllowedUsers().contains(user))) {
            System.out.println(p.get());
            return new ResponseEntity<>(p.get(), null, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, null, HttpStatus.FORBIDDEN);
    }
}
