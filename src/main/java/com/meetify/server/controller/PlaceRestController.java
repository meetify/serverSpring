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
    public PlaceRestController(UserRepository userRepository, PlaceRepository placeRepository, EntityManager entityManager) {
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
    public ResponseEntity<?> get(@RequestParam(value = "id") String id) {
        return new ResponseEntity<>(placeRepository.findById(new Id(Long.parseLong(id))).orElse(null),
                null, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> post(@RequestParam(value = "name") String name) {

        Place place = new Place(name);
        System.out.println(place);
        return new ResponseEntity<>(placeRepository.save(place), null, HttpStatus.OK);
    }

}
