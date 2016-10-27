package com.meetify.server.controller;

import com.meetify.server.model.Id;
import com.meetify.server.model.User;
import com.meetify.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashSet;
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
    
    private User get(Long id) {
        return userRepository.findById(new Id(id)).orElse(null);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> get(@RequestParam(value = "id") String id) {
        StringBuffer stringBuffer = new StringBuffer("");
        Arrays.stream(id.split(",")).forEach(s -> stringBuffer.append(get(Long.parseLong(s))));
        return new ResponseEntity<>(stringBuffer, null, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> post(@RequestParam(value = "id") String id,
                                  @RequestParam(value = "friends", defaultValue = "") String friends) {
        Set<Id> set = new HashSet<>();
        if (!friends.equals("")) {
            Arrays.stream(friends.replaceAll("[^,\\d]", "").split(",")).forEach(s -> set.add(new Id(Long.parseLong(s))));
        }
        return new ResponseEntity<>(userRepository.save(new User(new Id(Long.parseLong(id)), set)), null, HttpStatus.OK);
    }
}
