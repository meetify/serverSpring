package com.meetify.server.controller;

import com.meetify.server.model.Key;
import com.meetify.server.model.User;
import com.meetify.server.repository.KeyRepository;
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
import java.net.URL;
import java.util.Optional;

/**
 * com.meetify.server.controller
 * Created by kr3v on 08.10.2016.
 */
@RestController
@RequestMapping("/auth")
public class KeyRestController {

    private final UserRepository userRepository;
    private final KeyRepository keyRepository;

    @Autowired
    public KeyRestController(UserRepository userRepository, KeyRepository keyRepository) {
        this.userRepository = userRepository;
        this.keyRepository = keyRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> auth(@RequestParam(value = "dev") String devInfo,
                                  @RequestParam(value = "id") String vkID,
                                  @RequestParam(value = "key") String apiKey) throws IOException {
        String response = StringUtils.makeString(HttpRequest.request(
                new URL("https://api.vk.com/method/users.get?access_token=" + apiKey)))
                .toLowerCase();
        if (!response.contains(vkID) || response.contains("error")) {
            return new ResponseEntity<>(null, null, HttpStatus.FORBIDDEN);
        }
        Long uid = Long.parseLong(vkID);
        Optional<User> u = userRepository.findByVkID(uid);
        if (!u.isPresent()) {
            return new ResponseEntity<>(null, null, HttpStatus.FORBIDDEN);
        }
        User user = u.get();
        Optional<Key> k = keyRepository.findByUser(u.get());

        Key key;

        if (k.isPresent()) {
            if (k.get().usable() && k.get().getDevInfo().equals(devInfo)) {
                return new ResponseEntity<>(k.get(), null, HttpStatus.OK);
            } else {
                keyRepository.delete(k.get());
            }
        }

        key = new Key(user, devInfo);
        user.setKey(key);

        userRepository.save(user);
        keyRepository.save(key);
        return new ResponseEntity<>(key, null, HttpStatus.OK);
    }
}
