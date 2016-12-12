package com.meetify.server.controller

import com.meetify.server.model.entity.MeetifyLocation
import com.meetify.server.model.entity.Place
import com.meetify.server.service.LoginService
import com.meetify.server.service.PlaceService
import com.meetify.server.service.UserService
import com.meetify.server.util.JsonUtils.mapper
import com.meetify.server.util.WebUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

/**
 * This class represents controller over places. It holds mapping '/place'.
 * Also, it is layer between client and Google Places Web API.
 * @version 0.0.1
 * @since   0.0.1
 * @property    userService  users repo.
 * @constructor             Autowired by Spring.
 */
@RestController @RequestMapping("/place")
class PlaceController @Autowired constructor(
        private val userService: UserService,
        placeService: PlaceService,
        loginService: LoginService
) : AbstractController<Place>(placeService, loginService) {

    /**
     * Returns Google Place, that downloaded from it's server.
     * It has pre-converted photo links and it doesn't contain any places without photos.
     * @param   locationJson    json representation of location near of which places are looking.
     * @return                  google place, which can be easily serialized with Jackson JSON library.
     */
    @ResponseBody @GetMapping @RequestMapping("/nearby")
    fun nearby(@RequestParam("location") locationJson: String) = WebUtils
            .request(mapper.readValue(locationJson, MeetifyLocation::class.java), "100").apply {
        results = results.filter { it.photos.isNotEmpty() }.filter { it.types.contains("point_of_interest") }
        results.forEach { it.photos.forEach { it.photoReference = WebUtils.replaceRefs(it.photoReference) } }
    }
}