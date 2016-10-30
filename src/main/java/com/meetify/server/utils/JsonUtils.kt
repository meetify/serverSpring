package com.meetify.server.utils

//import com.fasterxml.jackson.module.kotlin.ExtensionsKt.*
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.meetify.server.model.Id
import java.util.*

/**
 * com.meetify.server.utils
 * Created by kr3v on 30.10.2016.
 */
object JsonUtils {
    val mapper = jacksonObjectMapper()

    fun getList(json: String): Collection<Id> {
        val ids = ArrayList<Id>()
        mapper.readValue(json, List::class.java).forEach {
            //failing of the next cast can be caused by incorrect data, so it can be thrown to user
            @Suppress("UNCHECKED_CAST")
            ids.add(Id((it as LinkedHashMap<String, Long>)["id"]!!))
        }
        return ids
    }
}
