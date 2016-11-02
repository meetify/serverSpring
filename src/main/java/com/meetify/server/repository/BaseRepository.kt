@file:Suppress("KDocUnresolvedReference")

package com.meetify.server.repository

import com.meetify.server.controller.BaseController
import com.meetify.server.model.BaseEntity
import com.meetify.server.model.Id
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.NoRepositoryBean
import java.util.*

/**
 * Interface, that marked as @NoRepositoryBean, because it is developed to be extended by another interfaces.
 * It extends usual JpaRepository with method [findById].
 * This allowed to develop [BaseController] with [BaseEntity] to simplify implementing
 * some another controllers by generifying methods.
 * @author      Dmitry Baynak
 * @version     0.0.1
 * @since       0.0.1
 */
@NoRepositoryBean
interface BaseRepository<T : BaseEntity> : JpaRepository<T, Id> {
    /**
     * This method allows finding object by param [id].
     * @param   id  id of object that should be found in database.
     * @return  optional that has information about found object.
     */
    fun findById(id: Id): Optional<T>
}