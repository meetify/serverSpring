package com.meetify.server.model.googleplace

import com.fasterxml.jackson.annotation.JsonInclude

import java.io.Serializable

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Viewport(var northeast: Location = Location(),
                    var southwest: Location = Location()) : Serializable