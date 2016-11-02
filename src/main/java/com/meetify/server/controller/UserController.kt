package com.meetify.server.controller

import com.meetify.server.model.Id
import com.meetify.server.model.User
import com.meetify.server.repository.UserRepository
import com.meetify.server.utils.JsonUtils.mapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.persistence.EntityManager

/**
 * This class represents controller over users. It holds mapping '/user'.
 * @author  Dmitry Baynak
 * @version 0.0.1
 * @since   0.0.1
 * @property   userRepository  users repository, which is provided by Spring & Hibernate.
 * @constructor             Autowired by Spring.
 */
@RestController @RequestMapping("/user")
class UserController @Autowired constructor(
        private val userRepository: UserRepository,
        entityManager: EntityManager) : BaseController<User>(userRepository, entityManager) {

    /**
     * Returns a list with information about friends of selected user.
     * If some friends are unknown, they are ignored.
     * @param   idJson  json representation of id of user, which friends should be returned.
     * @return          list with friends info.
     */
    @ResponseBody @RequestMapping("/friends") @GetMapping
    fun friends(@RequestParam(name = "id") idJson: String): ArrayList<User> = ArrayList<User>().apply {
        userRepository.findById(mapper.readValue(idJson, Id::class.java))
                .ifPresent { addAll(getFromCollection(it.friends)) }
    }

    override fun get(idsJson: String): ArrayList<User> {
        return super.get(idsJson)
    }

    override fun post(t: User, create: String): User {
        return super.post(t, create)
    }

    override fun put(t: User): User {
        return super.put(t)
    }

    override fun delete(t: User) {
        super.delete(t)
    }

    override fun getFromCollection(ids: Collection<Id>): ArrayList<User> {
        return super.getFromCollection(ids)
    }

    override fun runMaxQuery(t: User): Id {
        return super.runMaxQuery(t)
    }

    override fun generate(t: User): User {
        return super.generate(t)
    }
}