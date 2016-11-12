package com.meetify.server.controller

import com.meetify.server.model.Id
import com.meetify.server.model.entity.User
import com.meetify.server.repository.UserRepository
import com.meetify.server.utils.JsonUtils.mapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.persistence.EntityManager

/**
 * Цей клас є контролером користувачів.
 *
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
     * Метод, який використовується для пошуку друзів певного користувача.
     * Якщо певних друзів немає в базі, то вони ігноруються і не додаються до відповіді.
     * @param   idJson  ідентифікатор користувача, друзі якого мають бути повернені.
     * @return          список друзів переданого користувача.
     */
    @ResponseBody @RequestMapping("/friends") @GetMapping
    fun friends(@RequestParam(name = "id") idJson: String): ArrayList<User> = ArrayList<User>().apply {
        userRepository.findById(mapper.readValue(idJson, Id::class.java))
                .ifPresent { addAll(getFromCollection(it.friends)) }
    }

    override fun post(@RequestBody t: User, @RequestParam(name = "create", defaultValue = "") create: String): User {
        val u = userRepository.findById(t.id)
        if (u.isPresent) {
            val user = u.get()
            t.allowed = user.allowed
            t.created = user.created
        }
        return super.post(t, "")
    }

    /**
     * Заборонений для використання. Повертає UnsupportedOperationException при спробі використання.
     * @param   t json представлення екземляру T.
     * @return    збережений об'єкт.
     */
    override fun put(t: User): User {
        throw UnsupportedOperationException("no put in /user")
    }

    override fun get(idsJson: String): ArrayList<User> {
        return super.get(idsJson)
    }

    override fun delete(t: User) {
        super.delete(t)
    }
}