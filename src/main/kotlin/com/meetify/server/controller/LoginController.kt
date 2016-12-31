package com.meetify.server.controller

import com.meetify.server.model.UserExtended
import com.meetify.server.model.entity.Login
import com.meetify.server.model.entity.User
import com.meetify.server.service.LoginService
import com.meetify.server.util.JsonUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*

/**
 * This class represents controller over users. It holds mapping '/user'.
 * @since  0.1.0
 * @property  loginService Login service.
 * @constructor       Autowired by Spring.
 */
@RestController @RequestMapping("/login") @Service("LoginController")
class LoginController @Autowired constructor(private val loginService: LoginService) {

    /**
     * This class used just to receive [user] and [login] from client at once.
     * @property user given user.
     * @property login given login.
     * @constructor receives [user] and [login].
     */
    data class LoginUser(var user: User = User(), var login: Login = Login())

    /**
     * Login, register, getting additional information about user in this method.
     * Refers to [LoginService.login].
     * @property json [LoginUser] with given information.
     * @return information about user's friends, allowed and created places.
     */
    @ResponseBody @PostMapping
    fun login(@RequestBody json: String): UserExtended {
        println("LoginController.json: >$json<")
        val loginUser = JsonUtils.readJson(json, LoginUser::class.java)
        return loginService.login(loginUser.login, loginUser.user)
    }
}