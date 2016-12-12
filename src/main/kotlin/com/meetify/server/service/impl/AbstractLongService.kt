package com.meetify.server.service.impl

import com.meetify.server.model.entity.BaseEntity
import com.meetify.server.repository.BaseRepository

/**
 * Created by dmitry on 12/12/16.
 */
abstract class AbstractLongService<T : BaseEntity>(private val repo: BaseRepository<T, Long>)
    : AbstractService<T, Long>(repo) {
    override fun add(item: T): T = repo.save(item.apply { id = (repo.findMaxId() ?: -1) + 1 })

    override fun get(id: Long): T? = repo.findById(id)
}