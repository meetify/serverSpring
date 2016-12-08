package com.meetify.server.model.entity

import com.meetify.server.model.Id
import java.io.Serializable
import java.util.*
import javax.persistence.*


/**
 * This class is photo, that contains information about photo's id, owner id and uri.
 * @version     0.0.1
 * @since       0.0.1
 * @property    id          Id
 * @property    location    where is geographically place located.
 * @property    allowed     collection of places, where user has access.
 * @property    created     collection of places, which are created by this user.
 * @property    friends     collection of users, who are user's friends.
 * @constructor defined place's properties.
 */
@Suppress("unused")
@Entity
@Table(name = "users")
class User(@EmbeddedId override var id: Id = Id(0),
           @Embedded var location: Location = Location(),
           @ElementCollection var friends: Set<Id> = HashSet(),
           @ElementCollection var created: Set<Id> = HashSet(),
           @ElementCollection var allowed: Set<Id> = HashSet(),
           var name: String = "",
           var photo: String = "",
           var time: Long = 0,
           var vkAlbum: Long = 0
) : BaseEntity(id), Serializable {
    override fun isAvailableFor(id: Id): Boolean = id == this.id
}
