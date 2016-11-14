package com.meetify.server.model.entity

import com.meetify.server.model.Id
import java.io.Serializable
import java.util.*
import javax.persistence.*


/**
 * This class is place, that contains information place.
 * @version     0.0.1
 * @since       0.0.1
 * @property    name        place name.
 * @property    description place description.
 * @property    id          Id.
 * @property    owner       owner id.
 * @property    photo       photo.
 * @property    location    where is geographically place located.
 * @property    allowed     collection with users, which can view info about this place.
 * @constructor defined place's properties.
 */

@Table(name = "places")
@Entity
class Place(var name: String = "",
            var description: String = "",
            @AttributeOverride(name = "id", column = Column(name = "owner_id"))
            @Embedded val owner: Id = Id(-1),
            @AttributeOverride(name = "id", column = Column(name = "photo_id"))
            val photo: String = "",
            @EmbeddedId override var id: Id = Id(-1),
            @ElementCollection var allowed: Set<Id> = HashSet<Id>(),
            @Embedded var location: Location = Location()) : BaseEntity(id), Serializable {
    override fun isAvailable(id: Id): Boolean = allowed.contains(id) || owner == id
}