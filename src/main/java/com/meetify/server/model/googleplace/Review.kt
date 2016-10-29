package com.meetify.server.model.googleplace

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

import java.io.Serializable
import java.util.*

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Review(var aspects: List<Aspect> = ArrayList(),
                  @JsonProperty("author_name") var authorName: String = "",
                  @JsonProperty("author_url") var authorUrl: String = "",
                  var language: String = "",
                  var rating: Double = 0.0,
                  var text: String = "",
                  var time: Int = 0) : Serializable