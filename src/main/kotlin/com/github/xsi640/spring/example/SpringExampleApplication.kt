package com.github.xsi640.spring.example

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringExampleApplication

fun main(args: Array<String>) {
	runApplication<SpringExampleApplication>(*args)
}
