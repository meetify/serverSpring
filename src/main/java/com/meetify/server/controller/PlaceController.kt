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
 * Цей клас є контролером фотографій. Він також надає доступу до Google Places Web API разом із [GooglePlace].
 * @author  Дмитро Байнак
 * @version 0.0.1
 * @since   0.0.1
 * @property    userRepository  репозиторій користувачів.
 * @property    placeRepository репозиторій місць.
 * @param       entityManager   диспетчер сутностей.
 */

@RestController @RequestMapping("/place")
class PlaceController @Autowired constructor(
        private val userRepository: UserRepository,
        private val placeRepository: PlaceRepository,
        entityManager: EntityManager) : BaseController<Place>(placeRepository, entityManager) {

    /**
     * Метод, що повертає перечень місць, які були отримані за допомогою Google Places Web API.
     * Як додаток, сервер автоматично конвертує посилання на фото і не передає інформацію про ті місця, у яких немає фотографій.
     * @param   locationJson    json представлення координат місця, навколо якого ведеться пошук.
     * @return                  перелік місць.
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
    /**
     * Метод, який є перевизначенням існуючого для створення нових місць з певних генерованим ідентифікатором.
     * Якщо не має користувача, який мав би бути володарем цього місця, сервер передає виняток.
     * Якщо який-небудь переданий ідентифікатор у відповідній колекції користувачів,
     * які мають доступ до місця, не відповідає користувачеві, він ігнорується.
     * @param   t   місце, яке має бути створеним.
     * @return      створене місце.
     */
    override fun put(@RequestBody t: Place): Place {
        val place = super.put(t)
        userRepository.findById(place.owner).orElseThrow { IllegalArgumentException("owner not found") }.let {
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

    /**
     * Метод, який викликає метод [put] при заданому параметрі [create],
     * інакше просто у базі оновлюється переданий об'єкт.
     * @param   t   місце, яке має бути створено/оновлене.
     * @return      оновлене/створене місце.
     */
    override fun post(@RequestBody t: Place, @RequestParam(name = "create", defaultValue = "") create: String): Place {
        //todo: if create.isEmpty() => check that we're updating some existing object
        return if (create.trim().isEmpty()) return repo.save(t) else put(t)
    }

    override fun get(idsJson: String): ArrayList<Place> {
        return super.get(idsJson)
    }

    override fun delete(t: Place) {
        super.delete(t)
    }
}
