package com.meetify.server.repository

import com.meetify.server.model.entity.User

/**
 * Interface, that used to provide layer between database and server logic by autowiring by server.
 * This interface represents users.
 * @since    0.1.0
 */
interface UserRepository : BaseRepository<User, Long>
