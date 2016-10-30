package com.meetify.server.repository

import com.meetify.server.model.Id
import com.meetify.server.model.User
import java.util.*


interface UserRepository : CustomRepository<User, Long> {
    override fun findById(id: Id): Optional<User>
}
