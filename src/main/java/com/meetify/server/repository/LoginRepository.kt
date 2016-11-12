package com.meetify.server.repository

import com.meetify.server.model.entity.Login
import java.util.*

/**
 * Created by Maks on 05.11.2016.
 */
interface LoginRepository : BaseRepository<Login> {
    fun findByDevice(device: String): Optional<Login>
}