package com.meetify.server.service

import com.meetify.server.model.GooglePlace
import com.meetify.server.model.Location
import com.meetify.server.model.entity.Place

/**
 * Created by dmitry on 12/12/16.
 */
interface PlaceService : BaseService<Place, Long> {
    fun nearby(location: Location,
               radius: String = "100",
               types: String = "point_of_interest",
               name: String = ""): GooglePlace
}