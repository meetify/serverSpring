package com.meetify.server.model.entity

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Transient

/**
 * Created by Maks on 05.11.2016.
 */
@Entity
class Login(@Id @Column(name = "id") var device: String = "",
            @Column(name = "vk_id") override var id: Long = -1,
            @Transient var token: String = "") : BaseEntity(id), Serializable {
    override fun isAvailableFor(id: Long) = this.id == id
}