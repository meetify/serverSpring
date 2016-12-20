package com.meetify.server.model

import com.meetify.server.model.entity.Place
import com.meetify.server.model.entity.User
import java.util.*

/**
 *
 * @since 0.3.0
 * @property  allowed   collection of places, where user has access.
 * @property  created   collection of places, which are created by this user.
 * @property  friends   collection of users, who are user's friends.
 * @constructor can be empty or can take all needed sets.
 */
data class UserExtended(var friends: Set<User> = HashSet(),
                        var created: Set<Place> = HashSet(),
                        var allowed: Set<Place> = HashSet())