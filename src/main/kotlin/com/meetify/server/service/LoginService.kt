package com.meetify.server.service

import com.meetify.server.model.UserExtended
import com.meetify.server.model.entity.Login
import com.meetify.server.model.entity.User

/**
 * Service, that provides methods to allow logging in to implement some security.
 * @since 0.3.0
 */
interface LoginService : BaseService<Login, String> {

    /**
     * Login, register, getting additional information about user in this method.
     * @param user given user.
     * @param login given login.
     * @return user's allowed and created places, friends.
     */
    fun login(login: Login, user: User): UserExtended
}