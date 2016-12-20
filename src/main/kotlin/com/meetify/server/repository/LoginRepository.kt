package com.meetify.server.repository

import com.meetify.server.model.entity.Login

/**
 * Interface, that used to provide layer between database and server logic by autowiring by server.
 * This interface represents login.
 * @since    0.1.0
 */
interface LoginRepository : BaseRepository<Login, String> {
    /**
     * This method allows finding login by param [device].
     * @param  device device of login that should be found in database.
     * @return optional that has information about found login.
     */
    fun findByDevice(device: String): Login?
}

