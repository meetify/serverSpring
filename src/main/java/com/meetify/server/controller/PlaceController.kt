package com.meetify.server.controller

import com.meetify.server.model.*
import com.meetify.server.repository.PlaceRepository
import com.meetify.server.repository.UserRepository
import com.meetify.server.utils.JsonUtils.mapper
import com.meetify.server.utils.WebUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.persistence.EntityManager

/**
 * This class represents controller over places. It holds mapping '/place'.
 * Also, it is layer between client and Google Places Web API.
 * @author  Dmitry Baynak
 * @version 0.0.1
 * @since   0.0.1
 * @property    userRepository  users repository.
 * @property    placeRepository places repository.
 * @param       entityManager   entity manager.
 * @constructor             Autowired by Spring.
 */

@RestController @RequestMapping("/place")
class PlaceController @Autowired constructor(
        private val userRepository: UserRepository,
        private val placeRepository: PlaceRepository,
        entityManager: EntityManager) : BaseController<Place>(placeRepository, entityManager) {

    /**
     * Returns Google Place, that downloaded from it's server.
     * It has pre-converted photo links and it doesn't contain any places without photos.
     * @param   locationJson    json representation of location near of which places are looking.
     * @return                  google place, which can be easily serialized with Jackson JSON library.
     */
    @ResponseBody @RequestMapping("/nearby", method = arrayOf(RequestMethod.GET))
    fun nearby(@RequestParam(name = "location") locationJson: String): GooglePlace {
        val location = mapper.readValue(locationJson, Location::class.java)
        return WebUtils.request(location, "100").apply {
            results = results.filter { it.photos.size > 0 }
            results.forEach { it -> it.photos.forEach { it.photoReference = WebUtils.replaceRefs(it.photoReference) } }
        }
    }

//    @ResponseBody @RequestMapping(method = arrayOf(RequestMethod.POST))
//    fun post(@RequestBody place: Place): Place {
//        userRepository.findById(place.id).orElseThrow { throw IllegalArgumentException("owner not found") }.apply {
//            var usersToBeSaved: Set<User> = HashSet()
//            place.allowed.forEach {
//                userRepository.findById(it).ifPresent {
//                    it.allowed += place.id
//                    usersToBeSaved += it
//                }
//            }
//            this.created += place.id
//            usersToBeSaved += this
//            userRepository.save(usersToBeSaved)
//            placeRepository.save(place)
//        }
//        return place
//    }

    override fun get(idsJson: String): ArrayList<Place> = super.get(idsJson)

    override fun post(t: Place, create: String): Place = super.post(t, create)

    /**
     * Method, that should be used create new users places with some generated id.
     * If owner of this place is not present in database, IllegalArgumentException is thrown.
     * If some ids in allowed are not associated with existing users, they are ignored.
     * @param   t   place, which should be created.
     * @return      place, that was created if case of success.
     */
    override fun put(t: Place): Place {
        val place = super.put(t)
        userRepository.findById(place.id).orElseThrow { throw IllegalArgumentException("owner not found") }.let {
            HashSet<User>().apply {
                place.allowed.forEach {
                    userRepository.findById(it).ifPresent {
                        it.allowed += place.id
                        this += it
                    }
                }
                it.created += place.id
                userRepository.save(this)
            }
            return place
        }

    }

    override fun delete(t: Place) = super.delete(t)

    override fun getFromCollection(ids: Collection<Id>): ArrayList<Place> = super.getFromCollection(ids)

    override fun runMaxQuery(t: Place): Id = super.runMaxQuery(t)

    override fun generate(t: Place): Place = super.generate(t)
}
