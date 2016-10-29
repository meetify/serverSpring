package com.meetify.server.model

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Embeddable


@Suppress("unused")
@Embeddable
data class Id(@Column(insertable = false, updatable = false) val id: Long = 0) : Serializable {
    constructor(string: String) : this(string.toLong())
}
