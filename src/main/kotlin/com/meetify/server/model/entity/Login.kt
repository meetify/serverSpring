package com.meetify.server.model.entity

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Transient

/**
 * Login class, which used to create some security of user's data.
 * @since 0.1.0
 * @property device unique UUID, which generated on device and used to identify in methods.
 * @property id user's VK id.
 * @property token user's VK token, which used only to check it when logging in
 * @constructor defined login's properties.
 */
@Entity
class Login(@Id @Column(name = "id") val device: String = "",
            @Column(name = "vk_id") override var id: Long = -1,
            @Transient val token: String = "") : BaseEntity(id), Serializable {
    override fun isAvailableFor(id: Long) = this.id == id
}