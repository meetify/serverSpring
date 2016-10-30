package com.meetify.server

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.meetify.server.model.Id
import com.meetify.server.model.Location
import com.meetify.server.model.Place
import com.meetify.server.model.User
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class Application {
    companion object {
        @JvmStatic fun main(args: Array<String>) {
            SpringApplication.run(Application::class.java, *args)
            val o = jacksonObjectMapper()
            val a = "${o.writeValueAsString(User())}\n${o.writeValueAsString(Place())}\n${o.writeValueAsString(Id())}\n${o.writeValueAsString(Location())}"
            println(a)
//            val s = """[{"id":0},{"id":81},{"id":12}]"""
//            val f = o.readValue(s, List::class.java)
//            val ids = ArrayList<Id>()
//            f.forEach {
//                ids.add(Id((it as LinkedHashMap<*, *>)["id"].toString().toLong()))
//            }
//            ids.forEach(::println)

        }
    }
}