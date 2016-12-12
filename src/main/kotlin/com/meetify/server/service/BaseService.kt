package com.meetify.server.service

import java.io.Serializable
import java.util.*

/**
 * Created by dmitry on 12/12/16.
 */
interface BaseService<T, in V : Serializable> {
    fun add(item: T): T
    fun add(items: Collection<T>): Unit
    fun get(id: V): T?
    fun get(ids: Collection<V>): HashSet<T>
    fun delete(item: T)
    fun delete(id: V)
    fun edit(item: T): T
    fun edit(items: Collection<T>): Unit
}