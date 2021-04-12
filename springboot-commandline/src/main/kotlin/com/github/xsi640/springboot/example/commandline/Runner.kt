package com.github.xsi640.springboot.example.commandline

import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Component
class Runner1 : CommandLineRunner {
    private val log = LoggerFactory.getLogger(Runner1::class.java)

    override fun run(vararg args: String) {
        log.info("runner1 start. no order.")
    }
}

@Order(4)
@Component
class Runner2 : CommandLineRunner {
    private val log = LoggerFactory.getLogger(Runner2::class.java)
    override fun run(vararg args: String) {
        log.info("runner2 start. order = 4")
    }
}

@Order(3)
@Component
class Runner3 : CommandLineRunner {
    private val log = LoggerFactory.getLogger(Runner2::class.java)
    override fun run(vararg args: String) {
        log.info("runner2 start. order = 3")
    }
}
