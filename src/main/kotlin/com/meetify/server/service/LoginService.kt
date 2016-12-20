package com.meetify.server.service

import com.meetify.server.model.UserExtended
import com.meetify.server.model.entity.Login
import com.meetify.server.model.entity.User

/**
 * Created by dmitry on 12/12/16.
 */

interface LoginService : BaseService<Login, String> {
    fun login(login: Login, user: User): UserExtended
}