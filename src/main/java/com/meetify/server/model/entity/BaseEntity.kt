package com.meetify.server.model.entity

import com.meetify.server.model.Id

/**
 * Цей клас використовується для наслідування іншими класами, які мають [Id].

 * @version     0.0.1
 * @property    id  ідентифікатор.
 * @since       0.0.1
 */
abstract class BaseEntity(open var id: Id = Id()) {

    /**
     * Перевизначений із Any (аналог Object) метод equals.
     * @param   other   деякий об'єкт, із яким має бути порівняння за [id].
     * @return  булевий результат.
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this.javaClass !== other.javaClass) return false
        return id == (other as BaseEntity).id
    }

    /**
     * Перевизначений із Any (аналог Object) метод hashcode.
     * @return  хешкод ідентифікатору.
     */
    override fun hashCode() = id.hashCode()
}