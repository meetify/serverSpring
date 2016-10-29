package com.meetify.server.model.googleplace

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

import java.io.Serializable
import java.util.*

@JsonInclude(JsonInclude.Include.NON_NULL)
data class AddressComponent(@JsonProperty("long_name") var longName: String = "",
                            @JsonProperty("short_name") var shortName: String = "",
                            var types: List<String>? = ArrayList()) : Serializable