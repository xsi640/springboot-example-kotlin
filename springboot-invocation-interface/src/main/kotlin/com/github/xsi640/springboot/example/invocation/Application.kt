package com.github.xsi640.springboot.example.invocation

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableWebService("com.github.xsi640.springboot.example.invocation")
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}