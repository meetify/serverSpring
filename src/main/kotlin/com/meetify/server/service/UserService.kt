package com.meetify.server.service

import com.meetify.server.model.Location
import com.meetify.server.model.entity.User
import java.util.*

/**
 * Service, that provides methods to work with users.
 * @since 0.3.0
 */
interface UserService : BaseService<User, Long> {
    /**
     * Method which used to get list with information about friends of selected user.
     * If some friends are unknown, they are ignored.
     * @param  id user's id
     * @return  list with friends info
     */
    fun friends(id: Long): HashSet<User>

    /**
     * Method which used to update user's location.
     * @param location new user's location
     * @param user user
     */
    fun update(user: User, location: Location): User
}