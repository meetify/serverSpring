package com.meetify.server.controller

import com.meetify.server.model.Location
import com.meetify.server.model.entity.Place
import com.meetify.server.service.LoginService
import com.meetify.server.service.PlaceService
import com.meetify.server.util.JsonUtils.json
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

/**
 * This class represents controller over places. It holds mapping '/place'.
 * Also, it is layer between client and Google Places Web API.
 * @since  0.1.0
 * @constructor Autowired by Spring.
 */
@RestController @RequestMapping("/place")
class PlaceController @Autowired constructor(
        override val service: PlaceService,
        loginService: LoginService
) : AbstractController<Place>(service, loginService) {

    /**
     * Method, that used to get places from Google Places Web API.
     * It has pre-converted photo links and it doesn't contain any places without photos.
     * This method refers to [PlaceService.nearby].
     * @param  location json representation of location near of which places are looking.
     * @param  types the parameters that specify search. Examples are [here](https://developers.google.com/places/supported_types).
     * @param  name the parameter that allow searching by name.
     * @return google place, which can be easily serialized with Jackson JSON library.
     */
    @ResponseBody @GetMapping @RequestMapping("/nearby")
    fun nearby(@RequestParam("location") location: String,
               @RequestParam("types", defaultValue = "") types: String,
               @RequestParam("name", defaultValue = "") name: String
    ) = service.nearby(json(location, Location::class.java), "100", types, name)
}