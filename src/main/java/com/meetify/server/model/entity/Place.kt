package com.meetify.server.model.entity

import com.meetify.server.model.Id
import java.io.Serializable
import java.util.*
import javax.persistence.*


/**
 * Місце, яке створене певним користувачем із певними правами доступу для інших.

 * @version     0.0.1
 * @since       0.0.1
 * @property    name        назва.
 * @property    description деяка додаткова інформація.
 * @property    id          ідентифікатор.
 * @property    owner       ідентифікатор володаря.
 * @property    photo       ідентифікатор фотографії.
 * @property    location    розташування місця.
 * @property    allowed     список користувачів, які мають доступ до місця.
 * @constructor приймає певні необхідні параметри.
 */

@Table(name = "places")
@Entity
class Place(var name: String = "",
            var description: String = "",
            @AttributeOverride(name = "id", column = javax.persistence.Column(name = "owner_id"))
            @Embedded val owner: Id = Id(-1),
            @AttributeOverride(name = "id", column = javax.persistence.Column(name = "photo_id"))
            @Embedded val photo: Id = Id(-1),
            @EmbeddedId override var id: Id = Id(-1),
            @ElementCollection var allowed: Set<Id> = HashSet<Id>(),
            @Embedded var location: Location = Location()) : BaseEntity(id), Serializable