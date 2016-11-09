package com.meetify.server.controller

import com.meetify.server.model.entity.Login
import com.meetify.server.repository.LoginRepository
import com.meetify.server.repository.UserRepository
import com.meetify.server.utils.JsonUtils.mapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

/**
 * @suppress
 */
@RestController @RequestMapping("/login")
class LoginController @Autowired constructor(private val loginRepository: LoginRepository,
                                             private val userRepository: UserRepository) {
    private fun checkToken(token: String): Boolean = true
    private class NotFoundException : RuntimeException()

    @ResponseBody @GetMapping
    fun get(@RequestParam(name = "v") loginJson: String): Boolean {
        val loginReq = mapper.readValue(loginJson, Login::class.java)
        if (!checkToken(loginReq.token) || !userRepository.exists(loginReq.id)) {
            throw SecurityException("not correct token or owner")
        }
        val loginDB = loginRepository.findById(loginReq.id).orElseThrow { NotFoundException() }
        return loginDB.device.equals(loginReq.device)
    }

    @ResponseBody @PostMapping
    fun post(@RequestBody key: Login): Login {
        if (!checkToken(key.token) || !userRepository.exists(key.id)) {
            throw SecurityException("not correct token or owner")
        }
        return loginRepository.save(key)
    }
}