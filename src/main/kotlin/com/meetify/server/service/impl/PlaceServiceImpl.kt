package com.meetify.server.service.impl

import com.meetify.server.model.entity.Place
import com.meetify.server.model.entity.User
import com.meetify.server.repository.PlaceRepository
import com.meetify.server.service.PlaceService
import com.meetify.server.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

/**
 * Created by dmitry on 12/12/16.
 */
@Service
class PlaceServiceImpl
@Autowired constructor(repo: PlaceRepository,
                       private val userService: UserService)
    : AbstractLongService<Place>(repo), PlaceService {
    override fun add(item: Place): Place = place(null, super.add(item))

    override fun edit(item: Place): Place = place(get(item.id), super.edit(item))

    private fun place(old: Place?, new: Place): Place {
        HashSet<User>().apply {
            old?.let {
                (userService.get(old.owner))?.let {
                    old.allowed.map { userService.get(it)!! }.forEach {
                        it.allowed -= old.id
                        add(it)
                    }
                    it.created -= old.id
                    this += it
                }
            }
            (userService.get(new.owner) ?: throw IllegalArgumentException("owner not found")).let {
                new.allowed.map { userService.get(it)!! }.forEach {
                    it.allowed += new.id
                    this += it
                }
                it.created += new.id
                this += it
            }
            userService.edit(this)
        }
        return new
    }
}