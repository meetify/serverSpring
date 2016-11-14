package com.meetify.server.controller

import com.meetify.server.model.Id
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
class LoginController(private val loginRepository: LoginRepository,
                      private val userRepository: UserRepository,
                      ignored: String) {
    @Autowired constructor(log: LoginRepository,
                           usr: UserRepository) : this(log, usr, "") {
        repo = log
    }

    private fun checkToken(token: String): Boolean = !token.isEmpty()

    private class NotFoundException : RuntimeException()

    @ResponseBody @GetMapping
    fun get(@RequestParam(name = "v") loginJson: String): User {
        val loginReq = mapper.readValue(loginJson, Login::class.java)
        val user = userRepository.findById(loginReq.id)
        if (!user.isPresent) throw SecurityException("owner not found")
        val loginDB = loginRepository
                .findById(loginReq.id)
                .orElseThrow { NotFoundException() }
        return if (loginDB.device == loginReq.device) user.get() else User(Id(-1))
    }

    @ResponseBody @PostMapping
    fun post(@RequestBody key: Login): Login {
        if (!checkToken(key.token)) {
            throw SecurityException("not correct token ${mapper.writeValueAsString(key)}")
        }
        if (!userRepository.exists(key.id)) {
            throw SecurityException("not correct owner ${mapper.writeValueAsString(key)}")
        }
        findByDevice(key.device).ifPresent {
            repo?.delete(key.id)
        }
        key.token = ""
        return loginRepository.save(key)
    }

    companion object {
        var repo: LoginRepository? = null
        fun findByDevice(device: String) = repo!!.findByDevice(device)
    }
}