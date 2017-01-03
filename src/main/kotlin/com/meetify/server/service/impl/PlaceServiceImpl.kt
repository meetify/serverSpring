package com.meetify.server.service.impl

import com.meetify.server.model.GooglePlace
import com.meetify.server.model.Location
import com.meetify.server.model.entity.Place
import com.meetify.server.model.entity.User
import com.meetify.server.repository.PlaceRepository
import com.meetify.server.service.PlaceService
import com.meetify.server.service.UserService
import com.meetify.server.util.JsonUtils.readJson
import com.meetify.server.util.WebUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Service
import java.util.*

/**
 * @suppress
 */
@Service @Scope("singleton")
class PlaceServiceImpl
@Autowired constructor(repo: PlaceRepository,
                       private val userService: UserService)
    : AbstractLongService<Place>(repo), PlaceService {

    override fun add(item: Place): Place = place(null, super.add(item))

    override fun edit(item: Place): Place = place(get(item.id), super.edit(item))

    override fun nearby(location: Location, radius: String, types: String, name: String): GooglePlace {
        val url = "${url}location=$location&radius=$radius&types=$types&name=$name"
        val response = WebUtils.request(url).body().string()
        return readJson(response, GooglePlace::class.java).apply {
            results = results
                    .filter { it.photos.isNotEmpty() && it.types.contains("point_of_interest") }
                    .onEach { it.photos.forEach { it.photoReference.insert(0, photo) } }
        }
    }

    private fun place(old: Place?, new: Place): Place = new.apply {
        val users = HashSet<User>()
        old?.let {
            (userService.get(old.owner))?.let {
                //tag 1: we remove in that users, who isn't present in new allowed
                old.allowed.keys.filter { !new.allowed.containsKey(it) }
                        .let { userService.get(it) }
                        .apply { users += this }
                        .forEach { it.allowed.remove(old.id) }

                it.created -= old.id
                users.add(it)
            }
        }
        (userService.get(new.owner) ?: throw IllegalArgumentException("owner not found")).let {
            //if user was found in tag1, he has it.allowed[new.id], otherwise he's new at this place => false
            userService.get(new.allowed.keys)
                    .apply { users += this }
                    .forEach { it.allowed.put(new.id, it.allowed[new.id] ?: false) }
            it.created += new.id
            users.add(it)
        }
        userService.edit(users)
    }

    private companion object {
        private val key = "AIzaSyBgnGyxIek6PtMuVARZmVfaEtlH0Wiazms"
        private val url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?key=$key&"
        private val photo = "https://maps.googleapis.com/maps/api/place/photo?key=$key&maxwidth=600&photoreference="
    }
}


