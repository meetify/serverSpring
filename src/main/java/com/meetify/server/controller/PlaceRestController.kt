package com.meetify.server.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.meetify.server.model.Id
import com.meetify.server.model.Place
import com.meetify.server.model.User
import com.meetify.server.model.googleplace.GooglePlace
import com.meetify.server.model.googleplace.Photo
import com.meetify.server.model.googleplace.Result
import com.meetify.server.repository.PlaceRepository
import com.meetify.server.repository.UserRepository
import com.meetify.server.utils.HttpRequest
import com.meetify.server.utils.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*
import javax.persistence.EntityManager


@RestController
@RequestMapping("/place")
class PlaceRestController @Autowired constructor(private val userRepository: UserRepository, private val placeRepository: PlaceRepository, @SuppressWarnings("SpringJavaAutowiringInspection") entityManager: EntityManager) {
    init {
        val resp = entityManager.createQuery("select max(place.id) from Place as place").resultList[0]
        val id: Id
        if (resp == null) {
            id = Id(0.toLong())
        } else {
            id = resp as Id
        }
        Place.currentId = id.id + 1
    }

    @RequestMapping("/nearby")
    fun getNearby(@RequestParam(value = "lat", defaultValue = "48.468488") lat: String,
                  @RequestParam(value = "lon", defaultValue = "35.049684") lon: String,
                  @RequestParam(value = "radius", defaultValue = "100") radius: String): ResponseEntity<*> {
        try {
            lat.toDouble()
            lon.toDouble()
            radius.toDouble()
        } catch (e: Exception) {
            return ResponseEntity<Any>(null, null, HttpStatus.FORBIDDEN)
        }

        val objectMapper = jacksonObjectMapper()
        val googlePlace = objectMapper.readValue(StringUtils.makeString(HttpRequest.request(lat, lon, radius)), GooglePlace::class.java)
        googlePlace.sort().forEach { result -> result.photos.convertRefs(); }
        return ResponseEntity(googlePlace, null, HttpStatus.OK)

    }

    @RequestMapping(method = arrayOf(RequestMethod.GET))
    fun get(@RequestParam(value = "id") userIds: String): ResponseEntity<*> {
        val places = ArrayList<Place>()
        userIds.replace("[^,\\d]".toRegex(), "").split(",").forEach { placeRepository.findById(Id(it)).ifPresent { places.add(it) } }
        return ResponseEntity(places, null, HttpStatus.OK)
    }

    @RequestMapping(method = arrayOf(RequestMethod.POST))
    fun post(@RequestParam(value = "name") name: String,
             @RequestParam(value = "owner") owner: String,
             @RequestParam(value = "allowed", defaultValue = "") allowed: String): ResponseEntity<*> {
        var place = Place()
        userRepository.findById(Id(owner)).ifPresent { owner ->
            val usersToBeSaved = HashSet<User>()
            place = Place(name, owner.id)
            allowed.split(",").filter { it != "" }.forEach { id ->
                userRepository.findById(Id(id)).ifPresent { allowedUser ->
                    allowedUser.allowed += place.id
                    place.allowed += allowedUser.id
                    usersToBeSaved.add(allowedUser)
                }
            }
            owner.created += place.id
            usersToBeSaved.add(owner)
            userRepository.save(usersToBeSaved)
            placeRepository.save(place)
        }
        return ResponseEntity(place, null, HttpStatus.OK)
    }

    companion object {
        private fun List<Photo>.convertRefs() {
            forEach { photo -> photo.photoReference = HttpRequest.photoRefToUrl(photo.photoReference) }
        }

        private fun GooglePlace.sort(): List<Result> {
            results = results.filter { it.photos.size > 0 }
            return results
        }
    }
}
