package com.meetify.server.model.entity

import com.meetify.server.model.Id
import java.io.Serializable
import java.util.*
import javax.persistence.*


/**
 * Користувач, ідентифікатор якого відповідає його профілеві у соціальній мережі ВКонтакті.
 * @author      Дмитро Байнак
 * @version     0.0.1
 * @since       0.0.1
 * @property    id          ідентифікатор.
 * @property    location    де у поточний момент часу знаходиться користувач.
 * @property    allowed     список місць, до яких був запрошений даний користувач.
 * @property    created     список місць, які створив даний користувач.
 * @property    friends     список друзів користувача.
 * @constructor приймає певні необхідні параметри.
 */
@Entity
@Table(name = "users")
class User(@EmbeddedId override var id: Id = Id(0),
           @Embedded var location: Location = Location(),
           @ElementCollection var friends: Set<Id> = HashSet(),
           @ElementCollection var created: Set<Id> = HashSet(),
           @ElementCollection var allowed: Set<Id> = HashSet()) : BaseEntity(id), Serializable
