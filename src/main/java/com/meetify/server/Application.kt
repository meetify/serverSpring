package com.meetify.server

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.meetify.server.model.Id
import com.meetify.server.model.entity.*
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication


/**
 * Основний класс, який має аннотацію SpringBootApplication і відповідний статичний метод.
 * Використовується для запуску сервера.
 *
 * @version 0.0.1
 * @since   0.0.1
 */
@SpringBootApplication
open class Application {
    companion object {
        /**
         * Main-метод, у якому відбувається запуск сервера.
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