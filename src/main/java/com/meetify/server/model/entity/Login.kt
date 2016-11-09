package com.meetify.server.model.entity

import com.meetify.server.model.Id
import java.io.Serializable
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.Table

/**
 * @suppress
 */
@Entity
@Table(name = "logins")
class Login(@EmbeddedId override var id: Id = Id(-1),
            var token: String = "",
            var device: String = "") : BaseEntity(id), Serializable