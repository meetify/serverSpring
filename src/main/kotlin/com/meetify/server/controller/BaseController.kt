package com.meetify.server.controller

import com.meetify.server.model.entity.BaseEntity
import com.meetify.server.model.entity.Login
import com.meetify.server.service.BaseService
import java.util.*

/**
 * Simple interface that represents REST API for controller.
 * These methods are implemented in [AbstractController].
 * @since  0.3.0
 */
interface BaseController<T : BaseEntity> {
    /**
     * Returns a list with information about Ts.
     * Unknown or forbidden ids are ignored and not returned.
     * This method refers to [BaseService.get].
     * @param  ids   json representation of collection of the ids of requested Ts.
     * @param  device UUID that allows to find information about user. More about this in [Login].
     * @return collection with Ts info.
     */
    fun get(ids: HashSet<Long>, device: String): Collection<T>

    /**
     * Saves information about given [item].
     * If this item already was in database, service updates information about it in appropriate way.
     * Otherwise, it just saves new item into database with given id.
     * This method refers to [BaseService.edit].
     * @param  item  json representation of T instance.
     * @param  device UUID that allows to find information about user. More about this in [Login].
     * @return saved object
     */
    fun post(item: T, device: String): T

    /**
     * Add information about given [item] with generated id to database.
     * This method refers to [BaseService.add].
     * @param  item  json representation of T instance.
     * @param  device UUID that allows to find information about user. More about this in [Login].
     * @return saved object
     */
    fun put(item: T, device: String): T

    /**
     * Deletes given object.
     * This method refers to [BaseService.delete].
     * @param  item json representation of T instance.
     * @param  device UUID that allows to find information about user. More about this in [Login].
     */
    fun delete(item: T, device: String): Unit
}