package com.meetify.server.model

import java.io.Serializable
import javax.persistence.Embeddable

@Embeddable
data class Location(var lat: Double = 0.0, var lon: Double = 0.0) : Serializable