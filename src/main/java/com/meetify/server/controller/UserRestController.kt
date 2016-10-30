package com.meetify.server.controller

import com.meetify.server.model.Id
import com.meetify.server.model.User
import com.meetify.server.repository.UserRepository
import com.meetify.server.utils.JsonUtils
import com.meetify.server.utils.JsonUtils.mapper
import com.meetify.server.utils.MultiSearchable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*

/**
 * This class represents controller over users. It holds mapping '/user'.
 * @author  Dmitry Baynak
 * @version 0.0.1
 * @since   0.0.1
 * @param   userRepository  users repository, which is provided by Spring & Hibernate.
 * @constructor             Autowired by Spring.
 */
@RestController @RequestMapping("/user")
class UserRestController @Autowired constructor(private val userRepository: UserRepository) : MultiSearchable<User>() {
    /**
     * Returns a list with information about users.
     * Unknown ids in [idsJson] list are ignored.
     * @param   idsJson json representation of collection of the ids of requested users.
     * @return          collection with users info.
     */
    @ResponseBody @RequestMapping(method = arrayOf(RequestMethod.GET))
    fun get(@RequestParam(name = "ids") idsJson: String): ArrayList<User> {
        return get(userRepository, JsonUtils.getList(idsJson))
    }

    /**
     * Returns a list with information about friends of selected user.
     * If some friends are unknown, they are ignored.
     * @param   idJson  json representation of id of user, which friends should be returned.
     * @return          list with friends info.
     */
    @ResponseBody @RequestMapping("/friends", method = arrayOf(RequestMethod.GET))
    fun friends(@RequestParam(name = "id") idJson: String): ArrayList<User> {
        val id = mapper.readValue(idJson, Id::class.java)
        var friends = ArrayList<User>()
        userRepository.findById(id).ifPresent { friends = get(userRepository, it.friends) }
        return friends
    }

    /**
     * Put method, which is used to upgrade some info about user.
     * Mainly, should be used to send to server info about current location of this [user].
     * If [user] in body of request contains non-empty list of friends id, it also updated.
     * If some info about places (not location) in body is given, it's ignored.
     * @param   user  user, which should be updated with given information.
     */
    @ResponseBody @RequestMapping("/update", method = arrayOf(RequestMethod.PUT))
    fun update(@RequestBody user: User) {
        userRepository.findById(user.id).ifPresent {
            if (user.friends.size > 0) {
                it.friends = user.friends
            }
            it.location = user.location
            userRepository.save(it)
        }
    }

    /**
     * Method, that should be used only to register new users.
     * If [user] with the same id was found, given in request body user is ignored, and saved [user] is returned.
     * Otherwise, new [user] will be saved with given information.
     * If some info about places (not location) is given, it's ignored.
     * @param   user  user, which should be created.
     */
    @ResponseBody @RequestMapping(method = arrayOf(RequestMethod.POST))
    fun post(@RequestBody user: User): User {
        return userRepository.save(userRepository.findById(user.id).orElse(User(user.id, user.location, user.friends)))
    }
}