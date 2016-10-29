package com.meetify.server.model.googleplace

import com.fasterxml.jackson.annotation.JsonInclude

import java.io.Serializable

@JsonInclude(JsonInclude.Include.NON_NULL)
data class DayTime(var day: Int = 0,
                   var time: Int = 0) : Serializable