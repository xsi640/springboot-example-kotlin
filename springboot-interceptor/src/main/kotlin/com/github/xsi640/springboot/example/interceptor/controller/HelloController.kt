package com.github.xsi640.springboot.example.interceptor.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController {
    @GetMapping
    fun hello(): String {
        return "Hello world";
    }
}