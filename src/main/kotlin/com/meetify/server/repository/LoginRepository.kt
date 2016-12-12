package com.meetify.server.repository

import com.meetify.server.model.entity.Login

/**
 * Created by Maks on 05.11.2016.
 */
interface LoginRepository : BaseRepository<Login, String> {
    fun findByDevice(device: String): Login?
}

