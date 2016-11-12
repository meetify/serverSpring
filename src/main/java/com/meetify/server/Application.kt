package com.meetify.server

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication


/**
 * Main class, that is marked as SpringBootApplication, so it's used to launch server.
 * @version 0.0.1
 * @since   0.0.1
 * @constructor empty.
 */
@SpringBootApplication
open class Application {
    companion object {
        /**
         * Main method, that is used to run server.
         * @param   args    command-line options
         */
        @JvmStatic fun main(args: Array<String>) {
            SpringApplication.run(Application::class.java, *args)
//            val o = jacksonObjectMapper()
//            val a = "${o.writeValueAsString(User())}\n${o.writeValueAsString(Place())}" +
//                    "\n${o.writeValueAsString(Id())}\n${o.writeValueAsString(Location())}" +
//                    "\n${o.writeValueAsString(Photo())}\n${o.writeValueAsString(Login())}"
//            println(a)
//            val s = ArrayList<User>().apply {
//                add(User(Id(3)))
//                add(User(Id(2)))
//                add(User(Id(6)))
//            }
//
//            val s2 = o.writeValueAsString(s)
//            println(s2)
//            val s3 = (o.readValue(s2, Array<User>::class.java)).asList()
//            s3.forEach {
//                println("$it ${it.javaClass}")
//            }

        }
    }
}