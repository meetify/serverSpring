package com.meetify.server.model.googleplace

import com.fasterxml.jackson.annotation.JsonInclude

import java.io.Serializable

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Period(var open: DayTime = DayTime(),
                  var close: DayTime = DayTime()) : Serializable