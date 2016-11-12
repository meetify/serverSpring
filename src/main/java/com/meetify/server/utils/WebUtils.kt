package com.meetify.server.utils

import com.meetify.server.model.GooglePlace
import com.meetify.server.model.entity.Location
import com.meetify.server.utils.WebUtils.GOOGLE_API_KEY
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.*


/**
 * об'єкт, який містить у собі деякі методи для спрощення роботи з запитами до інших ресурсів.
 * @property GOOGLE_API_KEY Google API ключ.

 * @version     0.0.1
 * @since       0.0.1
 */
object WebUtils {
    val GOOGLE_API_KEY = "AIzaSyBgnGyxIek6PtMuVARZmVfaEtlH0Wiazms"

    /**
     * Метод, що використовується для виконання деяких HTTP(S) запитів.
     * @param url URL.
     * @return список рядків, які є відповіддю на запит.
     */
    fun request(url: URL): List<String> {
        val conn = url.openConnection() as HttpURLConnection
        conn.connect()
        BufferedReader(InputStreamReader(conn.inputStream)).use({ reader: BufferedReader ->
            val response = ArrayList<String>()
            reader.forEachLine {
                response.add(it.trim(' '))
            }

            return response
        })
    }

    /**
     * Метод, що використовується для отримання переліку місць із Google Place Web API.
     * @param location місце, навколо якого ведеться пошук.
     * @param radius радіус, із яким ведеться такий пошук.
     * @return екземпляр класу [GooglePlace].
     */
    fun request(location: Location, radius: String): GooglePlace {
        return JsonUtils.mapper.readValue(request(makeUrl(location, radius))
                .let({ StringBuilder().apply { it.forEach { append(it) } }.toString(); }), GooglePlace::class.java)
    }

    private fun makeUrl(location: Location, radius: String): URL {
        return URL("https://maps.googleapis.com/maps/api/place/nearbysearch/json?" +
                "location=${location.lat},${location.lon}&radius=$radius&key=$GOOGLE_API_KEY")
    }

    /**
     * Метод для конвертації звичайних ідентифікаторів (типу CoQBcwAAAF...)
     * фото з серверу Google на зручні для використання посилання.
     * @param photoRef посилання Google-типу.
     * @return конвертоване посилання.
     */
    fun replaceRefs(photoRef: String): String = "https://maps.googleapis.com/maps/api/place/photo?" +
            "photoreference=$photoRef&key=$GOOGLE_API_KEY&maxwidth=600"
}
