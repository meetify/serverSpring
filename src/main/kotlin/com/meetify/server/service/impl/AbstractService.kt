package com.meetify.server.service.impl

import com.meetify.server.model.entity.BaseEntity
import com.meetify.server.repository.BaseRepository
import com.meetify.server.service.BaseService
import java.io.Serializable
import java.util.*

/**
 * Created by dmitry on 12/12/16.
 */
abstract class AbstractService<T : BaseEntity, in V : Serializable>(private val repo: BaseRepository<T, V>)
    : BaseService<T, V> {
    override fun add(items: Collection<T>) = items.forEach { add(it) }

    override fun edit(items: Collection<T>) = items.forEach { edit(it) }

    override fun get(ids: Collection<V>): HashSet<T> = HashSet<T>().apply {
        ids.forEach { get(it)?.let { add(it) } }
    }

    override fun delete(item: T) = repo.delete(item)

    override fun delete(id: V) = repo.delete(id)

    override fun edit(item: T): T = repo.save(item)
}