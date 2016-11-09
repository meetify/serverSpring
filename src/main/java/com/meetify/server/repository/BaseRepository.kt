@file:Suppress("KDocUnresolvedReference")

package com.meetify.server.repository

import com.meetify.server.model.Id
import com.meetify.server.model.entity.BaseEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.NoRepositoryBean
import java.util.*

/**
 * Інтерфейс, який відмічен @NoRepositoryBean, адже він використовується для наслідування іншими інтерфейсами.
 * Розширення, у порівнянні із JpaRepository, полягає в [findById] методі.
 * Цей інтерфейс разом із [com.meetify.server.controller.BaseController] та [BaseEntity] були створені для спрощення окремих моментів у
 * реалізації контролерів.
 * @author      Дмитро Байнак
 * @version     0.0.1
 * @since       0.0.1
 */
@NoRepositoryBean
interface BaseRepository<T : BaseEntity> : JpaRepository<T, Id> {
    /**
     * Дозволяє знаходити об'єкт за допомогою [id].
     * @param   id  ідентифікатор, за яким ведеться пошук.
     * @return  монада Optional, яка містить інформацію про знайдений об'єкт.
     */
    fun findById(id: Id): Optional<T>
}