package com.meetify.server.model

import com.meetify.server.service.PlaceService
import java.io.Serializable
import javax.persistence.Embeddable

/**
 * This class is embedded location, that represents geographic coordinates of some object.
 * @since    0.1.0
 * @property  latitude latitude
 * @property  longitude longitude
 * @constructor takes 2 doubles.
 */
@Embeddable
data class Location(var latitude: Double = 0.0, var longitude: Double = 0.0) : Serializable {
    /**
     * Overridden method, which used in [PlaceService.nearby].
     * @return [latitude] and [longitude]
     */
    override fun toString(): String = "$latitude,$longitude"
}