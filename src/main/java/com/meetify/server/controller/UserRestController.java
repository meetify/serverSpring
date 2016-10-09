package com.meetify.server.controller;

import com.meetify.server.model.User;
import com.meetify.server.repository.UserRepository;
import com.meetify.server.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.Set;

/**
 * com.meetify.server.controller
 * Created by kr3v on 02.10.2016.
 */

@RestController
@RequestMapping("/user")
public class UserRestController {
    private final UserRepository userRepository;

    @Autowired
    public UserRestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@RequestParam(value = "id") String name) {
        Optional<User> u = userRepository.findByVkID(Long.parseLong(name));
        if (u.isPresent()) {
            return new ResponseEntity<>(u.get(), null, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> post(@RequestParam(value = "id") String id,
                                  @RequestParam(value = "friends", defaultValue = "") String friend) {
        if (friend.matches("[^,0-9]+") || id.equals("") || id.matches("[^0-9]+")) {
            return new ResponseEntity<>("illegal arguments", null, HttpStatus.NOT_ACCEPTABLE);
        }

        Long vkID = Long.parseLong(id);
        if (userRepository.findByVkID(vkID).isPresent()) {
            return new ResponseEntity<>("user with the same id is present in database",
                    null, HttpStatus.NOT_ACCEPTABLE);
        }

        this.userRepository.save(new User(vkID, StringUtils.setFromString(friend)));
        return new ResponseEntity<>("user with id " + id + " added to database",
                null, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<?> put(@RequestParam(value = "id") String id,
                                 @RequestParam(value = "friends", defaultValue = "") String friend) {

        if (!friend.matches("[\\d,]*") || !id.matches("\\d+")) {
            return new ResponseEntity<>("illegal arguments", null, HttpStatus.NOT_ACCEPTABLE);
        }

        Long vkID = Long.parseLong(id);
        Optional<User> user = this.userRepository.findByVkID(vkID);
        Set<Long> friends = StringUtils.setFromString(friend);
        if (user.isPresent()) {
            userRepository.save(user.get().setFriends(friends));
            return new ResponseEntity<>("user " + id + " friends updated",
                    null, HttpStatus.ACCEPTED);
        } else {
            userRepository.save(new User(vkID, friends));
            return new ResponseEntity<>("user with id " + id + " added to database",
                    null, HttpStatus.CREATED);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@RequestParam(value = "id") String id) {
        Optional<User> u = userRepository.findByVkID(Long.parseLong(id));
        if (u.isPresent()) {
            userRepository.delete(u.get());
            return new ResponseEntity<>("user " + id + " was deleted",
                    null, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(null,
                    null, HttpStatus.NOT_FOUND);
        }
    }
}
