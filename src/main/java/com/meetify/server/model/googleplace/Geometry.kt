package com.meetify.server.model.googleplace

import com.fasterxml.jackson.annotation.JsonInclude

import java.io.Serializable

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Geometry(var location: Location = Location(),
                    var viewport: Viewport = Viewport()) : Serializable
