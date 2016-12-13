package com.meetify.server.util

import com.meetify.server.model.GooglePlace
import com.meetify.server.model.entity.MeetifyLocation
import com.meetify.server.util.JsonUtils.json
import com.meetify.server.util.WebUtils.key
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.*


/**
 * This singleton contains some useful methods to work some web-requests.
 * @property key contains server's Google API key.
 * @version     0.0.1
 * @since       0.0.1
 */
object WebUtils {
    val key = "AIzaSyBgnGyxIek6PtMuVARZmVfaEtlH0Wiazms"

    /**
     * Method that can be used to perform some HTTP(S) requests.
     * @param url URL.
     * @return list of strings, that are response of request.
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
     * Method that can be used to get GooglePlace with some information about it.
     * @param location location of place, near of which places should be found.
     * @return parsed google place.
     */
    fun request(location: MeetifyLocation, radius: String, types: String, name: String)
            = json(request(url(location, radius, types, name))
            .let { StringBuilder().apply { it.forEach { append(it) } }.toString() }, GooglePlace::class.java)

    private fun url(location: MeetifyLocation) = StringBuffer("https://maps.googleapis.com/maps/api/place/nearbysearch" +
            "/json?location=${location.latitude},${location.longitude}")

    fun replaceRefs(photoRef: String): String = "https://maps.googleapis.com/maps/api/place/photo?" +
            "photoreference=$photoRef&key=$key&maxwidth=600"

    fun url(json: MeetifyLocation, radius: String, types: String, name: String) = URL(url(json).apply {
        mapOf(Pair("radius", radius), Pair("types", types), Pair("name", name), Pair("key", key)).forEach {
            if (it.value != "") append("&${it.key}=${it.value}")
        }
    }.toString())
}
