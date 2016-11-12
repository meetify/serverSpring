package com.meetify.server.utils

//import com.fasterxml.jackson.module.kotlin.ExtensionsKt.*
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.meetify.server.model.Id
import com.meetify.server.utils.JsonUtils.mapper
import java.util.*

/**
 * This singleton contains some useful methods to work with JSON.
 * @property mapper kotlin's mapper.
 * @version     0.0.1
 * @since       0.0.1
 */
object JsonUtils {
    val mapper = jacksonObjectMapper()

    /**
     * Method that used to escape parsing LinkedHashMap in each method.
     * If it fails on line with adding to ArrayList<Id> with casting, it's caused by incorrect data.
     * @param json json representation of Collection<Id>
     * @return parsed collection with ids.
     */
    fun getList(json: String): ArrayList<Id> {
        return ArrayList<Id>().apply {
            mapper.readValue(json, List::class.java).forEach {
                //failing of the next cast can be caused by incorrect data, so it can be thrown to user
                @Suppress("UNCHECKED_CAST")
                add(Id((it as LinkedHashMap<String, Long>)["id"]!!))
            }
        }
    }
}
