package com.meetify.server.utils

import com.meetify.server.model.Id
import com.meetify.server.repository.CustomRepository
import java.util.*

/**
 * com.meetify.server.utils
 * Created by kr3v on 30.10.2016.
 */
abstract class MultiSearchable<T> {
    fun get(repo: CustomRepository<T, Long>, ids: Collection<Id>): ArrayList<T> {
        val list = ArrayList<T>()
        ids.forEach { repo.findById(it).ifPresent { list.add(it) } }
        return list
    }
}