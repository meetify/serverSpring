package com.meetify.server.controller

import com.meetify.server.model.Location
import com.meetify.server.model.entity.User
import com.meetify.server.model.entity.Login
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
     * Method which used to get list with information about friends of selected user.
     * If some friends are unknown, they are ignored.
     * This method refers to [UserService.friends].
     * @param  device UUID that allows to find info about user. More about this in [Login].
     * @return  list with friends info.
     */
    @ResponseBody @RequestMapping("/friends") @GetMapping
    fun friends(@RequestParam(name = "device") device: String) = service.friends(login(device).id)

    /**
     * Method which used to update user's location.
     * This method refers to [UserService.update].
     * @param location new user's location.
     * @param device UUID that allows to find info about user. More about this in [Login].
     */
    @PostMapping @RequestMapping("/update")
    fun update(@RequestBody location: Location, @RequestParam("device") device: String
    ) = service.update(service.get(login(device).id) ?: throw SecurityException(), location)
}