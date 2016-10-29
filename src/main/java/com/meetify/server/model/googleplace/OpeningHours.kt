package com.meetify.server.model.googleplace

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

import java.io.Serializable
import java.util.*

@JsonInclude(JsonInclude.Include.NON_NULL)
data class OpeningHours(@JsonProperty("open_now") var openNow: Boolean = false,
                        @JsonProperty("weekday_text") var weekdayText: List<String> = ArrayList(),
                        var periods: List<Period> = ArrayList()) : Serializable