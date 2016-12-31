package com.meetify.server.model.entity

import com.meetify.server.model.Location
import java.io.Serializable
import javax.persistence.*

/**
 * This class is place, that contains information place.
 * @since    0.1.0
 * @property  name    place name.
 * @property  description place description.
 * @property  id     Id.
 * @property  owner    owner id.
 * @property  photo    photo.
 * @property  location  where is geographically place located.
 * @property  allowed   collection with users, which can view info about this place.
 * @constructor defined place's properties.
 */
@Table(name = "places")
@Entity
class Place(val name: String = "",
            val description: String = "",
            val owner: Long = -1,
            val photo: String = "",
            @Embedded val location: Location = Location(),
            @ElementCollection val allowed: Map<Long, Boolean> = HashMap<Long, Boolean>(),
            @Id override var id: Long = -1) : BaseEntity(id), Serializable {
    override fun isAvailableFor(id: Long): Boolean = allowed.contains(id) || owner == id
}