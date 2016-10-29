package com.meetify.server.model.googleplace

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

import java.io.Serializable

@JsonInclude(JsonInclude.Include.NON_NULL)
data class AlternativeId(@JsonProperty("place_id") var placeId: String = "",
                         var scope: String = "") : Serializable