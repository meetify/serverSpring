package com.meetify.server.repository

import com.meetify.server.model.Place

/**
 * Interface, that used to provide layer between database and server logic by autowiring by server.
 * This interface represents places.
 * @author      Dmitry Baynak
 * @version     0.0.1
 * @since       0.0.1
 */
interface PlaceRepository : BaseRepository<Place>
