package com.meetify.server.repository

import com.meetify.server.model.entity.Photo

/**
 * Interface, that used to provide layer between database and server logic by autowiring by server.
 * This interface represents photos.
 * @version     0.0.1
 * @since       0.0.1
 */
interface PhotoRepository : BaseRepository<Photo>