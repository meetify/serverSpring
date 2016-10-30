package com.meetify.server.utils

import com.meetify.server.model.Location
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.*


object HttpRequest {
    private val GOOGLE_API_KEY = "AIzaSyBgnGyxIek6PtMuVARZmVfaEtlH0Wiazms"

    private fun request(url: URL): List<String> {
        try {
            val conn = url.openConnection() as HttpURLConnection
            conn.connect()
            BufferedReader(InputStreamReader(conn.inputStream)).use { reader ->
                val response = ArrayList<String>()

                reader.forEachLine {
                    response.add(it.trim(' '))
                }

                return response
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return ArrayList()
    }

    fun request(location: Location, radius: String): List<String> {
        try {
            val url = URL("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" +
                    location.lat + "," + location.lon + "&" +
                    "radius=" + radius + "&" +
                    "key=" + GOOGLE_API_KEY)
            return request(url)
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }

        return ArrayList()
    }

    fun photoRefToUrl(photoRef: String): String {

        try {
            val conn = URL("https://maps.googleapis.com/maps/api/place/photo?" +
                    "photoreference=" + photoRef + "&" +
                    "key=" + GOOGLE_API_KEY + "&" +
                    "maxwidth=600").openConnection() as HttpURLConnection
            conn.instanceFollowRedirects = true
            conn.connect()
            return conn.url.toString()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return ""
    }
}
