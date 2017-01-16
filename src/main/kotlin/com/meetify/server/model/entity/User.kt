package com.meetify.server.model.entity

import com.meetify.server.model.Location
import java.io.Serializable
import java.util.*
import javax.persistence.*


/**
 * This class is photo, that contains information about photo's id, owner id and uri.
 * @since 0.1.0
 * @property id     Id
 * @property location where is geographically place located.
 * @property allowed collection of places, where user was invited.
 * @property created collection of places, which are created by this user.
 * @property friends collection of users, who are user's friends.
 * @property name user's VK name
 * @property photo user's VK photo
 * @property time when user last time given info to server.
 * @property vkAlbum
 * @constructor defined place's properties.
 */
@Suppress("unused")
@Entity
@Table(name = "users")
class User(@Id override var id: Long = -1,
           val name: String = "",
           val photo: String = "",
           val vkAlbum: Long = 0,
           var time: Long = 0,
           @ElementCollection val friends: Set<Long> = HashSet(),
           @ElementCollection var created: Set<Long> = HashSet(),
           @ElementCollection var allowed: MutableMap<Long, Boolean> = HashMap<Long, Boolean>(),
           @Embedded var location: Location = Location()) : BaseEntity(id), Serializable {
    override fun isAvailableFor(id: Long): Boolean = id == this.id || friends.contains(id)
}
