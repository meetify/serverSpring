package com.meetify.server.controller

import com.meetify.server.model.UserExtended
import com.meetify.server.model.entity.Login
import com.meetify.server.model.entity.User
import com.meetify.server.service.LoginService
import com.meetify.server.service.UserService
import com.meetify.server.util.JsonUtils.mapper
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
        private val placeController: PlaceController,
        private val userController: UserController) {

    private fun checkToken(token: String): Boolean = !token.isEmpty()

    data class LoginUser(var user: User = User(), var login: Login = Login())

    @ResponseBody @PostMapping @RequestMapping
    fun login(@RequestBody json: LoginUser): UserExtended {
        println(mapper.writeValueAsString(json))
        val user = json.user
        val login = json.login

        val loginOptional = loginService.get(login.device)
        val userOptional = userService.get(user.id)

        userOptional?.let {
            if (loginOptional != null && loginOptional.id != userOptional.id) {
                loginService.delete(loginOptional)
            }
            val userDB = userOptional
            user.allowed = userDB.allowed
            user.created = userDB.created
        } ?: loginOptional?.let {
            loginService.delete(loginOptional)
        }
        loginService.edit(login)
        userService.edit(user)
        //todo: the below looks very bad
        return UserExtended(
                userController.friends(login.device),
                placeController.get(user.created.toString(), login.device),
                placeController.get(user.allowed.toString(), login.device))
    }
}