package com.github.xsi640.springboot.example.aop.controller

import com.github.xsi640.springboot.example.aop.aop.Logging
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Logging
@RestController
class HelloController() {
    @GetMapping("/")
    fun hello(@RequestParam(name = "name") name: String): String {
        Thread.sleep(3000)
        return "Hello, $name"
    }
}