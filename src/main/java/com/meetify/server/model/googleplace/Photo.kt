package com.meetify.server.model.googleplace

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable
import java.util.*

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Photo(var width: Double? = 0.0,
                 var height: Double = 0.0,
                 @JsonProperty("html_attributions") var htmlAttributions: List<String> = ArrayList(),
                 @JsonProperty("photo_reference") var photoReference: String = "") : Serializable