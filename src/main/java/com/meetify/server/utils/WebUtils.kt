package com.meetify.server.utils

import com.meetify.server.model.GooglePlace
import com.meetify.server.model.Location
import com.meetify.server.utils.WebUtils.GOOGLE_API_KEY
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.*


/**
 * This singleton contains some useful methods to work some web-requests.
 * @property GOOGLE_API_KEY contains server's Google API key.
 * @author      Dmitry Baynak
 * @version     0.0.1
 * @since       0.0.1
 */
object WebUtils {
    private val GOOGLE_API_KEY = "AIzaSyBgnGyxIek6PtMuVARZmVfaEtlH0Wiazms"

    /**
     * Method that can be used to perform some HTTP(S) requests.
     * @param url URL.
     * @return list of strings, that are response of request.
     */
    fun request(url: URL): List<String> {
        val conn = url.openConnection() as HttpURLConnection
        conn.connect()
        BufferedReader(InputStreamReader(conn.inputStream)).use { reader ->
            val response = ArrayList<String>()
            reader.forEachLine {
                response.add(it.trim(' '))
            }

            return response
        }
    }

    /**
     * Method that can be used to get GooglePlace with some information about it.
     * @param location location of place, near of which places should be found.
     * @return parsed google place.
     */
    fun request(location: Location, radius: String): GooglePlace {
        return JsonUtils.mapper.readValue(request(URL("https://maps.googleapis.com/maps/api/place/nearbysearch/json?" +
                "location=${location.lat},${location.lon}&radius=$radius&key=$GOOGLE_API_KEY"))
                .let { StringBuilder().apply { forEach { append(it) } }.toString() }, GooglePlace::class.java)
    }

    /**
     * Method that can be used to convert unusable references like CoQBcwAAAF...
     * (trimmed due to big size) to useful links.
     * @param photoRef photo reference.
     * @return converted reference.
     */
    fun replaceRefs(photoRef: String): String = "https://maps.googleapis.com/maps/api/place/photo?" +
            "photoreference=$photoRef&key=$GOOGLE_API_KEY&maxwidth=600"
}
