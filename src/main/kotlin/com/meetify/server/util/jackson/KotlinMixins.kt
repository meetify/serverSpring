package com.meetify.server.util.jackson

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty

internal abstract class ClosedRangeMixin<T> @JsonCreator constructor(val start: T, @get:JsonProperty("end") val endInclusive: T) {
    @JsonIgnore abstract fun getEnd(): T
    @JsonIgnore abstract fun getFirst(): T
    @JsonIgnore abstract fun getLast(): T
    @JsonIgnore abstract fun getIncrement(): T
    @JsonIgnore abstract fun isEmpty(): Boolean
    @JsonIgnore abstract fun getStep(): T
}