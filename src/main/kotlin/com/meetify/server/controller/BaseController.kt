package com.meetify.server.controller

import com.meetify.server.model.entity.BaseEntity
import java.util.*

/**
 * Created by dmitry on 12/12/16.
 */
interface BaseController<T : BaseEntity> {
    fun get(ids: String, device: String): HashSet<T>
    fun post(t: T, device: String): T
    fun put(t: T, device: String): T
    fun delete(t: T, device: String): Unit
}