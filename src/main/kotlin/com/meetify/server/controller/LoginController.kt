package com.meetify.server.controller

import com.meetify.server.model.UserExtended
import com.meetify.server.model.entity.Login
import com.meetify.server.model.entity.User
import com.meetify.server.service.LoginService
import com.meetify.server.service.PlaceService
import com.meetify.server.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*

/**
 * Created by Maks on 05.11.2016.
 */
@Suppress("UNUSED_PARAMETER")
@RestController @RequestMapping("/login") @Service("LoginController") @Scope
class LoginController @Autowired constructor(
        private val loginService: LoginService,
        private val userService: UserService,
        private val placeService: PlaceService) {

    data class LoginUser(var user: User = User(), var login: Login = Login())

    @ResponseBody @PostMapping @RequestMapping
    fun login(@RequestBody json: LoginUser): UserExtended {
        loginService.login(json.login, json.user)
        return UserExtended(
                userService.friends(json.login.id),
                placeService.get(json.user.created),
                placeService.get(json.user.allowed))
    }
}