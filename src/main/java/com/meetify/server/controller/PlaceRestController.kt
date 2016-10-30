package com.meetify.server.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.meetify.server.model.Id
import com.meetify.server.model.Location
import com.meetify.server.model.Place
import com.meetify.server.model.User
import com.meetify.server.model.googleplace.GooglePlace
import com.meetify.server.model.googleplace.Photo
import com.meetify.server.model.googleplace.Result
import com.meetify.server.repository.PlaceRepository
import com.meetify.server.repository.UserRepository
import com.meetify.server.utils.HttpRequest
import com.meetify.server.utils.JsonUtils
import com.meetify.server.utils.JsonUtils.mapper
import com.meetify.server.utils.MultiSearchable
import com.meetify.server.utils.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.persistence.EntityManager

/**
 * This class represents controller over places. It holds mapping '/place'.
 * Also, it is layer between client and Google Places Web API.
 * It's
 * @author  Dmitry Baynak
 * @version 0.0.1
 * @since   0.0.1
 * @param   userRepository  users repository.
 * @param   placeRepository places repository.
 * @param   entityManager   entity manager.
 * @constructor             Autowired by Spring.
 */
@RestController @RequestMapping("/place")
class PlaceRestController @Autowired constructor(private val userRepository: UserRepository,
                                                 private val placeRepository: PlaceRepository,
                                                 entityManager: EntityManager) : MultiSearchable<Place>() {

    /**
     * Returns Google Place, that downloaded from it's server.
     * It has pre-converted photo links and it doesn't contain any places without photos.
     * @param   locationJson    json representation of location near of which places are looking.
     * @return                  google place, which can be easily serialized with Jackson JSON library.
     */
    @ResponseBody @RequestMapping("/nearby", method = arrayOf(RequestMethod.GET))
    fun getNearby(@RequestParam(name = "location") locationJson: String): GooglePlace {
        val location = mapper.readValue(locationJson, Location::class.java)
        val googlePlace = jacksonObjectMapper().readValue(StringUtils.makeString(HttpRequest.request(location, "100")), GooglePlace::class.java)
        googlePlace.filterPhotos().forEach { result -> result.photos.convertRefs(); }
        return googlePlace
    }

    /**
     * Returns a list with information about places.
     * Unknown ids in [idsJson] list are ignored.
     * @param   idsJson json representation of collection of the ids of requested places.
     * @return          collection with places info.
     */
    @ResponseBody @RequestMapping(method = arrayOf(RequestMethod.GET))
    fun get(@RequestParam(name = "ids") idsJson: String): ArrayList<Place> {
        return get(placeRepository, JsonUtils.getList(idsJson))
    }

    /**
     * Method, that should be used create new users places.
     * Always create new place, even if place with exactly the same info is already present in database.
     * If owner of this place is not present in database, IllegalArgumentException is thrown.
     * If some ids in allowed are not associated with existing users, they are ignored.
     * @param   place   place, which should be created.
     * @return          place, that was created if case of success.
     */
    @ResponseBody @RequestMapping(method = arrayOf(RequestMethod.POST))
    fun post(@RequestBody place: Place): Place {
        userRepository.findById(place.id).orElseThrow { throw IllegalArgumentException("owner not found") }.apply {
            var usersToBeSaved: Set<User> = HashSet()
            place.allowed.forEach {
                userRepository.findById(it).ifPresent {
                    it.allowed += place.id
                    usersToBeSaved += it
                }
            }
            this.created += place.id
            usersToBeSaved += this
            userRepository.save(usersToBeSaved)
            placeRepository.save(place)
        }
        return place
    }

    /**
     * Dirty hack with places id. I wasn't able to create autoincrement embedded id, so it can be done with
     * simple searching max of existing ids, or, if it's not presented in database, set it as 1.
     */
    init {
        Place.currentId = 0
        Optional.ofNullable(entityManager
                .createQuery("select max(place.id) from Place as place")
                .resultList[0] as Id?).ifPresent {
            Place.currentId += it!!.id
        }
    }

    /**
     * Contains some useful methods, which are used to simplify code of some other methods.
     */
    companion object {

        /**
         * This function is used to convert simple photo references to downloadable links.
         */
        private fun List<Photo>.convertRefs() {
            forEach { photo -> photo.photoReference = HttpRequest.photoRefToUrl(photo.photoReference) }
        }

        /**
         * This function is used to discard places, that don't contain any photos.
         */
        private fun GooglePlace.filterPhotos(): List<Result> {
            results = results.filter { it.photos.size > 0 }
            return results
        }
    }
}
