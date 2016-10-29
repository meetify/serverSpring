package com.meetify.server.repository

import com.meetify.server.model.Id
import com.meetify.server.model.Place
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*


interface PlaceRepository : JpaRepository<Place, Long> {
    fun findById(id: Id): Optional<Place>
}
