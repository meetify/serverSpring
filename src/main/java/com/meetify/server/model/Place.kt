package com.meetify.server.model

import java.io.Serializable
import java.util.*
import javax.persistence.*


@Suppress("unused")
@Table(name = "places")
@Entity
data class Place(@Column(name = "place_name") var name: String = "",
                 @Embedded @Column(name = "owner_id") val owner: Id = Id(-1),
                 @EmbeddedId @Column(name = "place_id") val id: Id = if (owner.id == Id(-1).id) Id() else nextId(),
                 @ElementCollection var allowed: Set<Id> = HashSet<Id>(),
                 @Embedded var location: Location = Location()) : Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this.javaClass !== other.javaClass) return false
        return id == (other as Place).id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    /**
     * Part of dirty hack, which is described at PlaceRestRepository.init.
     */
    companion object {
        var currentId: Long = 0

        fun nextId(): Id {
            return Id(currentId++)
        }
    }
}
