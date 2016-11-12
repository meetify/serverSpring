package com.meetify.server.controller

import com.meetify.server.model.Id
import com.meetify.server.model.entity.Login
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
    fun get(@RequestParam(name = "v") loginJson: String): Boolean {
        val loginReq = mapper.readValue(loginJson, Login::class.java)
        if (!userRepository.exists(loginReq.id)) {
            throw SecurityException("not correct token or owner")
        }
        val loginDB = loginRepository
                .findById(loginReq.id)
                .orElseThrow { NotFoundException() }
        return loginDB.device == loginReq.device
    }

    @ResponseBody @PostMapping
    fun post(@RequestBody key: Login): Login {
        if (!checkToken(key.token) || !userRepository.exists(key.id)) {
            throw SecurityException("not correct token or owner")
        }
        key.token = ""
        return loginRepository.save(key)
    }

    companion object {

        var repo: LoginRepository? = null

        fun findById(id: Id) = repo!!.findById(id)

        fun findByDevice(device: String) = repo!!.findByDevice(device)

        fun check(id: Id, device: String): Boolean {
            try {
                return findById(id)
                        .orElseThrow { Exception() }
                        .let { it.device == device }
            } catch (e: Exception) {
                return false
            }
        }

    }
}