package com.meetify.server.repository

import com.meetify.server.model.User

/**
 * Interface, that used to provide layer between database and server logic by autowiring by server.
 * This interface represents users.
 * @author      Dmitry Baynak
 * @version     0.0.1
 * @since       0.0.1
 */
interface UserRepository : BaseRepository<User>
