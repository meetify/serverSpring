package com.meetify.server.model

import java.io.Serializable
import java.util.*
import javax.persistence.*


@SuppressWarnings("unused")

@Entity
@Table(name = "users")
data class User(@EmbeddedId var id: Id = Id(0),
                @Embedded var location: Location = Location(),
                @ElementCollection var friends: Set<Id> = HashSet(),
                @ElementCollection var created: Set<Id> = HashSet(),
                @ElementCollection var allowed: Set<Id> = HashSet()) : Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this.javaClass !== other.javaClass) return false
        return id == (other as User).id

    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}

