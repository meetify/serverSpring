package com.meetify.server.model

import com.meetify.server.model.entity.Place
import com.meetify.server.model.entity.User
import java.util.*

/**
 * Created on 08.12.2016.
 * @author  Dmitry Baynak
 */
data class UserExtended(var friends: Set<User> = HashSet(),
                        var created: Set<Place> = HashSet(),
                        var allowed: Set<Place> = HashSet())