package com.meetify.server.service.impl

import com.meetify.server.model.UserExtended
import com.meetify.server.model.entity.Login
import com.meetify.server.model.entity.User
import com.meetify.server.repository.LoginRepository
import com.meetify.server.service.LoginService
import com.meetify.server.service.PlaceService
import com.meetify.server.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Service

/**
 * @suppress
 */
@Service @Scope("singleton")
class LoginServiceImpl @Autowired constructor(
        override val repo: LoginRepository,
        private val userService: UserService,
        private val placeService: PlaceService)
    : AbstractService<Login, String>(repo), LoginService {

    private fun checkToken(token: String): Boolean = !token.isEmpty()

    override fun login(login: Login, user: User): UserExtended {
        if (!checkToken(login.token)) throw SecurityException(login.token)
        val loginDB = get(login.device)
        val userDB = userService.get(user.id)

        userDB?.let {
            if (loginDB != null && loginDB.id != userDB.id) {
                delete(loginDB)
            }
            user.allowed = userDB.allowed
            user.created = userDB.created
        } ?: loginDB?.let {
            delete(loginDB)
        }

        edit(login)
        userService.edit(user)

        return userService.update(user)
    }

    override fun add(item: Login): Login = repo.save(item)

    override fun get(id: String): Login? = repo.findByDevice(id)
}
