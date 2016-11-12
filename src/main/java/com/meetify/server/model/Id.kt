package com.meetify.server.model

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Embeddable

/**
 * This class is embedded id for all entity classes.
 * It's expressed in [BaseEntity] which contains instance of Id.
 * @version     0.0.1
 * @since       0.0.1
 * @property    id  id
 * @constructor takes [id].
 */
@Embeddable
data class Id(@Column(insertable = false, updatable = false)
              var id: Long = 0) : Serializable
