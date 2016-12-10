package com.meetify.server.repository

import com.meetify.server.model.entity.Login
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

/**
 * Created by Maks on 05.11.2016.
 */
interface LoginRepository : JpaRepository<Login, String> {
    fun findByDevice(device: String): Optional<Login>
}

