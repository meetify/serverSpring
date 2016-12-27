package com.meetify.server.service.impl

import com.meetify.server.model.GooglePlace
import com.meetify.server.model.Location
import com.meetify.server.model.entity.Place
import com.meetify.server.model.entity.User
import com.meetify.server.repository.PlaceRepository
import com.meetify.server.service.PlaceService
import com.meetify.server.service.UserService
import com.meetify.server.util.JsonUtils
import com.meetify.server.util.WebUtils
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

    override fun nearby(location: Location, radius: String, types: String, name: String): GooglePlace {
        val url = StringBuilder(url).apply {
            mapOf(Pair("location", location), Pair("radius", radius), Pair("types", types), Pair("name", name), Pair("key", key)
            ).forEach {
                append("${it.key}=${it.value}&")
            }
        }.toString()
        return JsonUtils.json(WebUtils.request(url).body().string(), GooglePlace::class.java).apply {
            results = results.filter { it.photos.isNotEmpty() && it.types.contains("point_of_interest") }.apply {
                forEach { it.photos.forEach { it.photoReference = photo + it.photoReference } }
            }
        }
    }


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

    private companion object {
        private val key = "AIzaSyBgnGyxIek6PtMuVARZmVfaEtlH0Wiazms"
        private val url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?"
        private val photo = "https://maps.googleapis.com/maps/api/place/photo?key=$key&maxwidth=600&photoreference="
    }
}
