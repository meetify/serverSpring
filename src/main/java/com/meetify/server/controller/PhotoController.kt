package com.meetify.server.controller

import com.meetify.server.model.Id
import com.meetify.server.model.Photo
import com.meetify.server.repository.PhotoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*
import javax.persistence.EntityManager

/**
 * This class represents controller over photos. It holds mapping '/photo'.
 * @author  Dmitry Baynak
 * @version 0.0.1
 * @since   0.0.1
 * @property    photoRepository Photo repository.
 * @constructor Autowired by Spring.
 */
@RestController @RequestMapping("/photo")
class PhotoController @Autowired constructor(
        private val photoRepository: PhotoRepository,
        entityManager: EntityManager) : BaseController<Photo>(photoRepository, entityManager) {
    override fun get(idsJson: String): ArrayList<Photo> {
        return super.get(idsJson)
    }

    override fun post(t: Photo, create: String): Photo {
        return super.post(t, create)
    }

    override fun put(t: Photo): Photo {
        return super.put(t)
    }

    override fun delete(t: Photo) {
        super.delete(t)
    }

    override fun getFromCollection(ids: Collection<Id>): ArrayList<Photo> {
        return super.getFromCollection(ids)
    }

    override fun runMaxQuery(t: Photo): Id {
        return super.runMaxQuery(t)
    }

    override fun generate(t: Photo): Photo {
        return super.generate(t)
    }
}
