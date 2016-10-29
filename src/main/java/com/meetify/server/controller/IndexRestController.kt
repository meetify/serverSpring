package com.meetify.server.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class IndexRestController {
    @RequestMapping("/")
    fun index(): String {
        return "Hi, we are trying to be on https!"
    }
}
