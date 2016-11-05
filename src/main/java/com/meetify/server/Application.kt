package com.meetify.server

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.meetify.server.model.Id
import com.meetify.server.model.entity.*
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication


/**
 * Main class, that is marked as SpringBootApplication, so it's used to launch server.
 * @author  Dmitry Baynak
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
            val o = jacksonObjectMapper()
            val a = "${o.writeValueAsString(User())}\n${o.writeValueAsString(Place())}" +
                    "\n${o.writeValueAsString(Id())}\n${o.writeValueAsString(Location())}" +
                    "\n${o.writeValueAsString(Photo())}\n${o.writeValueAsString(Login())}"
            println(a)
        }
    }
}