package com.github.xsi640.springboot.example.invocation

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@WebService
interface WebServiceDemo {
    fun get(): String
}

@RestController
class Controller(
    private val webServiceDemo: WebServiceDemo
) {

    @GetMapping
    fun get(): String {
        return webServiceDemo.get()
    }
}