package com.meetify.server.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * com.meetify.server.controller
 * Created by kr3v on 08.10.2016.
 */

@RestController
public class IndexRestController {
    @RequestMapping("/")
    public String index() {
        return "Hi, we are trying to be on https!";
    }
}
