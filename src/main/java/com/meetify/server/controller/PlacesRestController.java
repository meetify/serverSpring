package com.meetify.server.controller;

import com.meetify.server.utils.HttpRequest;
import com.meetify.server.utils.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


}
