package com.meetify.server.controller

import com.meetify.server.model.entity.Photo
import com.meetify.server.repository.PhotoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
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
        entityManager: EntityManager) : BaseController<Photo>(photoRepository, entityManager)