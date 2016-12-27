package com.meetify.server.util

import com.meetify.server.util.JsonUtils.mapper
import com.meetify.server.util.jackson.jacksonObjectMapper

/**
 * This singleton contains some useful methods to work with JSON.
 * @property mapper kotlin's mapper.
 * @since    0.1.0
 */
object JsonUtils {
    val mapper = jacksonObjectMapper()

    /**
     * Alias to deserialize objects.
     * @param json json representation of some class
     * @param clazz Class<T>, which are trying are deserialize.
     * @return instance of deserialized [json]
     */
    fun <V> json(json: String, clazz: Class<V>) = mapper.readValue(json, clazz)!!

    /**
     * Alias to serialize objects.
     * @param json instance of any class
     * @return serialized object
     */
    fun json(json: Any) = mapper.writeValueAsString(json)!!
}
