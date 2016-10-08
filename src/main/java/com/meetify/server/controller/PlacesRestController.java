package com.meetify.server.controller;

import com.meetify.server.utils.HttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * com.meetify.server.controller
 * Created by kr3v on 08.10.2016.
 */
@RestController
@RequestMapping("/places")
public class PlacesRestController {
    @RequestMapping("/getNearby")
    public String getNearby(@RequestParam(value = "lat") String lat,
                            @RequestParam(value = "lon") String lng,
                            @RequestParam(value = "radius") String radius) throws IOException {
        StringBuilder response = new StringBuilder();
        HttpRequest.request(lat, lng, radius).forEach(s -> response.append(s).append("\n"));
        return response.toString();
    }

}
