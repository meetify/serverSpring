package com.meetify.server.controller

import com.meetify.server.model.UserExtended
import com.meetify.server.model.entity.Login
import com.meetify.server.model.entity.User
import com.meetify.server.repository.LoginRepository
import com.meetify.server.repository.UserRepository
import com.meetify.server.utils.JsonUtils.mapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

/**
 * Created by Maks on 05.11.2016.
 */
@Suppress("UNUSED_PARAMETER")
@RestController @RequestMapping("/login")
class LoginController private constructor(private val loginRepository: LoginRepository,
                                          private val userRepository: UserRepository,
                                          private val placeController: PlaceController,
                                          private val userController: UserController,
                                          ignored: String) {

    @Autowired constructor(loginRepository: LoginRepository,
                           userRepository: UserRepository,
                           placeController: PlaceController,
                           userController: UserController)
    : this(loginRepository, userRepository, placeController, userController, "") {
        repo = loginRepository
    }

    private fun checkToken(token: String): Boolean = !token.isEmpty()

    @Deprecated("LoginController.login replaced this quite well.")
    @ResponseBody @GetMapping
    fun get(@RequestParam(name = "v") loginJson: String): User {
        val loginReq = mapper.readValue(loginJson, Login::class.java)
        val user = userRepository.findById(loginReq.id)
        val loginDB = loginRepository.findByDevice(loginReq.device).orElse(Login())
        return if (loginDB.id == loginReq.id) user.get() else User()
    }

    @Deprecated("LoginController.login replaced this quite well.")
    @ResponseBody @PostMapping
    fun post(@RequestBody key: Login): Login {
        if (!checkToken(key.token)) {
            throw SecurityException("not correct token ${mapper.writeValueAsString(key)}")
        }
        if (!userRepository.exists(key.id)) {
            throw SecurityException("not correct owner ${mapper.writeValueAsString(key)}")
        }
        findByDevice(key.device).ifPresent {
            repo?.delete(key.device)
        }
        key.token = ""
        return loginRepository.save(key)
    }

    data class LoginUser(var user: User = User(), var login: Login = Login())

    @ResponseBody @PostMapping @RequestMapping("/auto")
    fun login(@RequestBody json: LoginUser): UserExtended {
        println(mapper.writeValueAsString(json))
        val user = json.user
        val login = json.login

        val loginOptional = loginRepository.findByDevice(login.device)
        val userOptional = userRepository.findById(user.id)

        if (userOptional.isPresent) {
            if (loginOptional.isPresent && loginOptional.get().id != userOptional.get().id) {
                loginRepository.delete(loginOptional.get())
            }
            val userDB = userOptional.get()
            user.allowed = userDB.allowed
            user.created = userDB.created
        } else if (loginOptional.isPresent) {
            loginRepository.delete(loginOptional.get())
        }
        loginRepository.save(login)
        userRepository.save(user)
        return UserExtended(
                userController.friends(login),
                placeController.getFromCollection(user.created, login),
                placeController.getFromCollection(user.allowed, login))
    }

    companion object {
        var repo: LoginRepository? = null
        fun findByDevice(device: String) = repo!!.findByDevice(device)
    }
}