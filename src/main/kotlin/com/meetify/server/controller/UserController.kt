package com.meetify.server.controller

import com.meetify.server.model.entity.MeetifyLocation
import com.meetify.server.model.entity.User
import com.meetify.server.service.LoginService
import com.meetify.server.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

/**
 * This class represents controller over users. It holds mapping '/user'.
 * @version 0.0.1
 * @since   0.0.1
 * @property   service      users repo, which is provided by Spring & Hibernate.
 * @constructor             Autowired by Spring.
 */
@RestController @RequestMapping("/user")
class UserController @Autowired constructor(
        override val service: UserService,
        loginService: LoginService) : AbstractController<User>(service, loginService) {

    /**
     * Returns a list with information about friends of selected user.
     * If some friends are unknown, they are ignored.
     * @param   device  json representation of id of user, which friends should be returned.
     * @return          list with friends info.
     */
    @ResponseBody @RequestMapping("/friends") @GetMapping
    fun friends(@RequestParam(name = "device") device: String) = service.friends(login(device).id)

    @PostMapping @RequestMapping("/update")
    fun update(@RequestBody location: MeetifyLocation, @RequestParam("device") device: String
    ) = service.update(service.get(login(device).id) ?: throw SecurityException(), location)
}