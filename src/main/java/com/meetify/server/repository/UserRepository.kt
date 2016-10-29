package com.meetify.server.repository

import com.meetify.server.model.Id
import com.meetify.server.model.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*


interface UserRepository : JpaRepository<User, Long> {
    fun findById(id: Id): Optional<User>
}
