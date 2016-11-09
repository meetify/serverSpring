package com.meetify.server.utils

//import com.fasterxml.jackson.module.kotlin.ExtensionsKt.*
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.meetify.server.model.Id
import com.meetify.server.utils.JsonUtils.mapper
import java.util.*

/**
 * Цей об'єкт має в собі методи для роботи з JSON.
 * @property mapper об'єкт, який виконує серіалізацію/десеріалізацію.
 * @author      Дмитро Байнак
 * @version     0.0.1
 * @since       0.0.1
 */
object JsonUtils {
    val mapper = jacksonObjectMapper()

    /**
     * Метод, який використовується для позбавлення від LinkedHashMap під час десеріалізації
     * списку ідентифікаторів.
     * @param json json представлення колекції ідентифікаторів.
     * @return отримана колекція ідідентифікаторів.
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
