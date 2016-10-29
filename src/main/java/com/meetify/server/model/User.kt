package com.meetify.server.model

import java.io.Serializable
import java.util.*
import javax.persistence.ElementCollection
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.Table


@SuppressWarnings("unused")

@Entity
@Table(name = "users")
data class User(@EmbeddedId var id: Id = Id(0),
                @ElementCollection var friends: Set<Id> = HashSet<Id>(),
                @ElementCollection var created: Set<Id> = HashSet<Id>(),
                @ElementCollection var allowed: Set<Id> = HashSet<Id>()) : Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this.javaClass !== other.javaClass) return false
        return id == (other as User).id

    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
