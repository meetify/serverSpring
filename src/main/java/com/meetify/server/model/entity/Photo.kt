package com.meetify.server.model.entity

import com.meetify.server.model.Id
import java.io.Serializable
import javax.persistence.*

/**
 * Фотографія, яка використовується у певному об'єкті.

 * @version     0.0.1
 * @since       0.0.1
 * @property    id      ідентифікатор фотографії.
 * @property    owner   ідентифікатор володаря фотографії.
 * @property    uri     розташування фотографії (як file:///, так і http(s)://)
 * @constructor приймає певні необхідні параметри.
 */

@Entity
@Table(name = "photos")
class Photo(
        @EmbeddedId override var id: Id = Id(),
        @AttributeOverride(name = "id", column = javax.persistence.Column(name = "owner_id"))
        @Embedded var owner: Id = Id(),
        var uri: String = "") : BaseEntity(id), Serializable