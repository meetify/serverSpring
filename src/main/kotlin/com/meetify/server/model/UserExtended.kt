package com.meetify.server.model

import com.meetify.server.model.entity.Place
import com.meetify.server.model.entity.User
import java.io.Serializable
import java.util.*

/**
 * Used to send extended information to client about user places and friends.
 * @since 0.3.0
 * @property  allowed   collection of places, where user has access.
 * @property  created   collection of places, which are created by this user.
 * @property  friends   collection of users, who are user's friends.
 * @constructor can be empty or can take all needed sets.
 */
data class UserExtended(val friends: Set<User> = HashSet(),
                        val created: Set<Place> = HashSet(),
                        val allowed: Set<Place> = HashSet()) : Serializable