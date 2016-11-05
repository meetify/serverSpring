package com.meetify.server.model.entity

import java.io.Serializable
import javax.persistence.Embeddable

/**
 * This class is embedded location, that represents geographic coordinates of some object.
 * @author      Dmitry Baynak
 * @version     0.0.1
 * @since       0.0.1
 * @property    lat  latitude
 * @property    lon  longitude
 * @constructor takes 2 doubles.
 */
@Embeddable
data class Location(var lat: Double = 0.0, var lon: Double = 0.0) : Serializable