package com.meetify.server.controller

import com.meetify.server.model.Location
import com.meetify.server.model.entity.Login
import com.meetify.server.model.entity.Place
import com.meetify.server.model.entity.User
import com.meetify.server.service.LoginService
import com.meetify.server.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

/**
 * This class represents controller over users. It holds mapping '/user'.
 * @since  0.1.0
 * @property service users repo, which is provided by Spring & Hibernate.
 * @constructor       Autowired by Spring.
 */
@RestController @RequestMapping("/user")
class UserController @Autowired constructor(
        override val service: UserService,
        loginService: LoginService) : AbstractController<User>(service, loginService) {

    /**
     * Returns list with information about user's friends.
     * If some friends are unknown, they are ignored.
     * Refers to [UserService.friends].
     * @param  device UUID that allows to find info about user. More about this in [Login].
     * @return  list with friends info.
     */
    @RequestMapping("/friends") @GetMapping
    fun friends(@RequestParam(name = "device") device: String): Set<User>
            = service.friends(login(device).id)

    /**
     * Updates user's location and last online time.
     * Refers to [UserService.update].
     * @param location new user's location.
     * @param device UUID that allows to find info about user. More about this in [Login].
     * @return user with updated location and online time information.
     */
    @RequestMapping("/update") @PostMapping
    fun update(@RequestBody location: Location, @RequestParam("device") device: String): User =
            service.update(service.get(login(device).id)!!, location)

    /**
     * Returns allowed places, which weren't already seen by user.
     * Refers to [UserService.unvisited].
     * @param device UUID that allows to find info about user. More about this in [Login].
     * @return new allowed places.
     */
    @GetMapping @RequestMapping("/unvisited")
    fun unvisited(@RequestParam device: String): Set<Place> =
            service.unvisited(service.get(login(device).id)!!)
}