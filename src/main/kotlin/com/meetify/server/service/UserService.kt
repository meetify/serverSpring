package com.meetify.server.service

import com.meetify.server.model.Location
import com.meetify.server.model.entity.Place
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
     * Updates user's location and last online time.
     * @param location new user's location
     * @param user user
     */
    fun update(user: User, location: Location): User

    /**
     * Returns list of allowed places, that weren't seen by given user.
     * @param user user
     */
    fun unvisited(user: User): HashSet<Place>
}