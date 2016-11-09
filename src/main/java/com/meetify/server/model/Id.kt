package com.meetify.server.model

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Embeddable

/**
 * Цей клас використовується як вбудовуваний ідентифікатор для всіх класів, які є JPA-сутностями.
 * @author      Дмитро Байнак
 * @version     0.0.1
 * @since       0.0.1
 * @property    id  ідентифікатор
 * @constructor приймає [id].
 */
@Embeddable
data class Id(@Column(insertable = false, updatable = false) var id: Long = 0) : Serializable
