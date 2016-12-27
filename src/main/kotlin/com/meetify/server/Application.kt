package com.meetify.server

import com.meetify.server.util.jackson.jacksonObjectMapper
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean


/**
 * Main class, that is marked as SpringBootApplication, so it's used to launch server.
 * @since  0.1.0
 */
@SpringBootApplication
open class Application {
    companion object {
        /**
         * Main method, that is used to run server.
         * @param  args  command-line options
         */
        @JvmStatic fun main(args: Array<String>) {
            val o = jacksonObjectMapper()
            Jackson2ObjectMapperFactoryBean().setObjectMapper(jacksonObjectMapper())
            SpringApplication.run(Application::class.java, *args)
            val a = hashSetOf(1, 2, 3)
            val z = o.writeValueAsString(a)
            println(z)
            val aa = o.readValue(z, HashSet<Int>().javaClass)
            println(aa)
        }
    }
}