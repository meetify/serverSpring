package com.meetify.server.controller

import com.meetify.server.model.entity.User
import com.meetify.server.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.persistence.EntityManager

/**
 * This class represents controller over users. It holds mapping '/user'.
 * @version 0.0.1
 * @since   0.0.1
 * @property   userRepository  users repository, which is provided by Spring & Hibernate.
 * @constructor             Autowired by Spring.
 */
@RestController @RequestMapping("/user")
class UserController @Autowired constructor(
        userRepository: UserRepository,
        entityManager: EntityManager) : BaseController<User>(userRepository, entityManager) {

    /**
     * todo that's fails
     * Returns a list with information about friends of selected user.
     * If some friends are unknown, they are ignored.
     * @param   device  json representation of id of user, which friends should be returned.
     * @return          list with friends info.
     */
    @ResponseBody @RequestMapping("/friends") @GetMapping
    fun friends(@RequestParam(name = "device") device: String): Collection<User> = ArrayList<User>().apply {
        repo.findById(getLogin(device).id).get().let { it -> it.friends.forEach { add(repo.findById(it).get()) } }
    }

    override fun post(@RequestBody t: User,
                      @RequestParam(name = "create", defaultValue = "") create: String,
                      @RequestParam(name = "device") device: String): User {
        check(t, device)
        repo.findById(t.id).ifPresent {
            t.allowed = it.allowed
            t.created = it.created
        }
        return repo.save(t)
    }

    override fun put(t: User, device: String): User = throw UnsupportedOperationException("no put in /user")
}