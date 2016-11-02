package com.meetify.server.repository

import com.meetify.server.model.Photo

/**
 * Interface, that used to provide layer between database and server logic by autowiring by server.
 * This interface represents photos.
 * @author      Dmitry Baynak
 * @version     0.0.1
 * @since       0.0.1
 */
interface PhotoRepository : BaseRepository<Photo>