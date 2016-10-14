package com.meetify.server.controller;

import com.meetify.server.model.Id;
import com.meetify.server.model.Place;
import com.meetify.server.repository.PlaceRepository;
import com.meetify.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import java.util.Arrays;

/**
 * Created by dmitry on 10/14/16.
 */

@RestController
@RequestMapping("/place")
public class PlaceRestController {
    private final UserRepository userRepository;
    private final PlaceRepository placeRepository;
    private final EntityManager entityManager;

    @Autowired
    public PlaceRestController(UserRepository userRepository, PlaceRepository placeRepository, @SuppressWarnings("SpringJavaAutowiringInspection") EntityManager entityManager) {
        this.userRepository = userRepository;
        this.placeRepository = placeRepository;
        this.entityManager = entityManager;
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
            placeRepository.save(place);
            user1.getCreated().add(place);
            userRepository.save(user1);
            placeRepository.save(place);
            response.append(place.toString());
        });
        return new ResponseEntity<>(response.toString(), null, HttpStatus.OK);
    }

}
