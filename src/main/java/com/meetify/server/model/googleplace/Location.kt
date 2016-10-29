package com.meetify.server.model.googleplace

import com.fasterxml.jackson.annotation.JsonInclude

import java.io.Serializable

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0) : Serializable
