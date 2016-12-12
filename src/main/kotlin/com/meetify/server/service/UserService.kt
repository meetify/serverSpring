package com.meetify.server.service

import com.meetify.server.model.entity.MeetifyLocation
import com.meetify.server.model.entity.User
import java.util.*

/**
 * Created by dmitry on 12/12/16.
 */
interface UserService : BaseService<User, Long> {
    fun update(user: User, location: MeetifyLocation): User
    fun friends(id: Long): HashSet<User>
}