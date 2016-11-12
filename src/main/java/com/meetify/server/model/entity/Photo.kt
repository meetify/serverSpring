package com.meetify.server.model.entity

import com.meetify.server.model.Id
import java.io.Serializable
import javax.persistence.*

/**
 * This class is photo, that contains information about photo's id, owner id and uri.
 * @version     0.0.1
 * @since       0.0.1
 * @property    id      Id
 * @property    owner   owner id
 * @property    uri     where is photo located (it can be as file:/// or http(s)://)
 * @constructor defined photo's properties.
 */

@Entity
@Table(name = "photos")
class Photo(
        @EmbeddedId override var id: Id = Id(),
        @AttributeOverride(name = "id", column = javax.persistence.Column(name = "owner_id"))
        @Embedded var owner: Id = Id(),
        var uri: String = "") : BaseEntity(id), Serializable {
    override fun isAvailable(id: Id): Boolean = true
}