package com.meetify.server.repository

import com.meetify.server.controller.AbstractController
import com.meetify.server.model.entity.BaseEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.NoRepositoryBean
import java.io.Serializable

/**
 * Interface, that marked as @NoRepositoryBean, because it is developed to be extended by another interfaces.
 * It extends usual JpaRepository with method [findById].
 * This allowed to develop [AbstractController] with [BaseEntity] to simplify implementing
 * some another controllers by generifying methods.
 * @since    0.1.0
 */
@NoRepositoryBean
interface BaseRepository<T : BaseEntity, V : Serializable> : JpaRepository<T, V> {
    /**
     * This method allows finding object by param [id].
     * @param  id id of object that should be found in database.
     * @return optional that has information about found object.
     */
    fun findById(id: V): T?

    /**
     * This method allows finding maximum id in table for given entity.
     * @return optional with V
     */
    @Query("select max(t.id) from #{#entityName} as t")
    fun findMaxId(): V?
}