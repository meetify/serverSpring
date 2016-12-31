package com.meetify.server.service

import com.meetify.server.model.GooglePlace
import com.meetify.server.model.Location
import com.meetify.server.model.entity.Place

/**
 * Service, that provides methods to work with places and Google Place Web API.
 * @since 0.3.0
 */
interface PlaceService : BaseService<Place, Long> {

    /**
     * Method, that used to get places from Google Places Web API.
     * It has pre-converted photo links and it doesn't contain any places without photos.
     * @param location json representation of location near of which places are looking.
     * @param types params to specify search. Examples are [here](https://developers.google.com/places/supported_types).
     * @param name allows searching by name.
     * @param radius radius of searching
     * @return google place, which can be easily serialized with Jackson JSON library.
     */
    fun nearby(location: Location,
               radius: String = "100",
               types: String = "point_of_interest",
               name: String = ""): GooglePlace
}