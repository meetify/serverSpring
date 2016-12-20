package com.meetify.server.util

import okhttp3.OkHttpClient
import okhttp3.Request


/**
 * This singleton contains some useful methods to work some web-requests.
 * @property client OkHttpClient instance using which [request]s are done.
 * @since    0.1.0
 */
object WebUtils {
    val client = OkHttpClient()

    /**
     * Method that can be used to perform some HTTP(S) requests.
     * Implemented using OkHttp3
     * @param url URL.
     * @return response of request.
     */
    fun request(url: String) = client.newCall(Request.Builder().get().url(url).build()).execute()!!
}
