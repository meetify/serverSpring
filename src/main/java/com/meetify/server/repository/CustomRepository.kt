package com.meetify.server.repository

import com.meetify.server.model.Id
import org.springframework.data.jpa.repository.JpaRepository
import java.io.Serializable
import java.util.*

/**
 * com.meetify.server.repository
 * Created by kr3v on 30.10.2016.
 */
interface CustomRepository<T, ID : Serializable?> : JpaRepository<T, ID> {
    fun findById(id: Id): Optional<T>
}