package com.meetify.server.repository

import com.meetify.server.model.Id
import com.meetify.server.model.Place
import java.util.*


interface PlaceRepository : CustomRepository<Place, Long> {
    override fun findById(id: Id): Optional<Place>
}
