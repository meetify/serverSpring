package com.meetify.server.utils

import java.util.*


object StringUtils {
    fun setFromString(s: String): HashSet<Long> {
        val answer = HashSet<Long>()
        if (s != "") {
            s.split(",").forEach { answer.add(it.toLong()) }
        }
        return answer
    }

    fun makeString(request: List<String>): String {
        val sb = StringBuilder("")
        request.forEach { sb.append(it) }
        return sb.toString()
    }
}
