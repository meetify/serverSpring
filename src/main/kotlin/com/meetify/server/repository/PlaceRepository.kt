package com.meetify.server.repository

import com.meetify.server.model.entity.Place

/**
 * Interface, that used to provide layer between database and server logic by autowiring by server.
 * This interface represents places.
 * @since    0.1.0
 */
interface PlaceRepository : BaseRepository<Place, Long>
