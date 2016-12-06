package com.meetify.server.controller

import com.meetify.server.model.GooglePlace
import com.meetify.server.model.entity.Location
import com.meetify.server.model.entity.Place
import com.meetify.server.model.entity.User
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
 * @version 0.0.1
 * @since   0.0.1
 * @property    userRepository  users repository.
 * @property    repo            places repository.
 * @param       entityManager   entity manager.
 * @constructor             Autowired by Spring.
 */

@RestController @RequestMapping("/place")
class PlaceController @Autowired constructor(
        private val userRepository: UserRepository,
        placeRepository: PlaceRepository,
        entityManager: EntityManager) : BaseController<Place>(placeRepository, entityManager) {

    /**
     * Returns Google Place, that downloaded from it's server.
     * It has pre-converted photo links and it doesn't contain any places without photos.
     * @param   locationJson    json representation of location near of which places are looking.
     * @return                  google place, which can be easily serialized with Jackson JSON library.
     */
    @ResponseBody @GetMapping @RequestMapping("/nearby")
    fun nearby(@RequestParam(name = "location") locationJson: String): GooglePlace = WebUtils
            .request(mapper.readValue(locationJson, Location::class.java), "100").apply {
        results = results.filter { it.photos.size > 0 }.filter { it.types.contains("point_of_interest") }
        results.forEach { it.photos.forEach { it.photoReference = WebUtils.replaceRefs(it.photoReference) } }
    }

    /**
     * Method, that should be used create new users places with some generated id.
     * If owner of this place is not present in database, IllegalArgumentException is thrown.
     * If some ids in allowed are not associated with existing users, they are ignored.
     * @param   t   place, which should be created.
     * @return      place, that was created if case of success.
     */
    @ResponseBody @PutMapping
    override fun put(@RequestBody t: Place,
                     @RequestParam(name = "device") device: String): Place = this.post(t, "create", device)

    /**
     * Method, that should be used create new users places with some generated id.
     * If owner of this place is not present in database, IllegalArgumentException is thrown.
     * If some ids in allowed are not associated with existing users, they are ignored.
     * @param   t   place, which should be created.
     * @return      place, that was created if case of success.
     */
    @ResponseBody @PostMapping
    override fun post(@RequestBody t: Place,
                      @RequestParam(name = "create", defaultValue = "") create: String,
                      @RequestParam(name = "device") device: String
    ): Place = userRepository.findById(t.owner).orElseThrow { IllegalArgumentException("owner not found") }.let {
        val place = super.post(t, create, device)
        HashSet<User>().apply {
            place.allowed.forEach {
                userRepository.findById(it).ifPresent {
                    it.allowed += place.id
                    this += it
                }
            }
            it.created += place.id
            this += it
            userRepository.save(this)
        }
        repo.save(place)
    }
}