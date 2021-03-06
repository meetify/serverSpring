package com.meetify.server.service.impl

import com.meetify.server.model.entity.BaseEntity
import com.meetify.server.repository.BaseRepository
import com.meetify.server.service.BaseService
import java.io.Serializable
import java.util.*

/**
 * @suppress
 */
abstract class AbstractService<T : BaseEntity, V : Serializable>(open val repo: BaseRepository<T, V>)
    : BaseService<T, V> {
    override fun add(items: Collection<T>): Unit = items.forEach { add(it) }

    override fun get(ids: Collection<V>): HashSet<T> = ids.map { get(it) }.filterNotNull().toHashSet()

    override fun edit(items: Collection<T>): Unit = items.forEach { edit(it) }

    override fun edit(item: T): T = repo.save(item)

    override fun delete(item: T): Unit = repo.delete(item)

    override fun delete(id: V): Unit = repo.delete(id)
}