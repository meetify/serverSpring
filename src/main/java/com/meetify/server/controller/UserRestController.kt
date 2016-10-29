package com.meetify.server.controller

import com.meetify.server.model.Id
import com.meetify.server.model.User
import com.meetify.server.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*


@RestController
@RequestMapping("/user")
class UserRestController
@Autowired
constructor(private val userRepository: UserRepository) {

    private fun get(id: Long): User {
        return userRepository.findById(Id(id)).orElse(null)
    }

    @RequestMapping(method = arrayOf(RequestMethod.GET))
    fun get(@RequestParam(value = "id") id: String): ResponseEntity<*> {
        val users = ArrayList<User>()
        id.split(",").forEach { userRepository.findById(Id(it)).ifPresent { users.add(it) } }
        return ResponseEntity(users, null, HttpStatus.OK)
    }

    @RequestMapping(method = arrayOf(RequestMethod.POST))
    fun post(@RequestParam(value = "id") id: String,
             @RequestParam(value = "friends", defaultValue = "") friends: String): ResponseEntity<*> {
        val friendsSet = HashSet<Id>()
        if (friends != "") {
            friends.replace("[^,\\d]".toRegex(), "").split(",").forEach { s -> friendsSet.add(Id(s)) }
        }
        userRepository.save(User(Id(id.toLong()), friendsSet))
        return ResponseEntity("well done", null, HttpStatus.OK)
    }
}
