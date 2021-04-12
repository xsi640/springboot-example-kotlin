package com.github.xsi640.springboot.example.async.controller

import com.github.xsi640.springboot.example.async.service.TaskService
import kotlinx.coroutines.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController {
    @Autowired
    private lateinit var taskService: TaskService

    @GetMapping("/hello")
    fun hello(): String {
        taskService.doTaskOne()
        taskService.doTaskTwo()
        taskService.doTaskThree()
        return "finish"
    }

    @GetMapping("/hello2")
    fun hello2(name: String): String {
        val r1 = runBlocking { taskService.doTaskCallbackOne(name) }
        val r2 = runBlocking { taskService.doTaskCallbackTwo(name) }
        val r3 = runBlocking { taskService.doTaskCallbackThree(name) }

        return "$r1 $r2 $r3"
    }

    @GetMapping("/hello3")
    suspend fun asyncHello(name: String): String {
        val r1 = taskService.doTaskCallbackOne(name)
        val r2 = taskService.doTaskCallbackTwo(name)
        val r3 = taskService.doTaskCallbackThree(name)

        return "$r1 $r2 $r3"
    }

    @ExperimentalCoroutinesApi
    @GetMapping("/hello4")
    suspend fun asyncHello2(name: String): String {
        val r1 = GlobalScope.async {
            taskService.doTaskCallbackOne(name)
        }
        val r2 = GlobalScope.async {
            taskService.doTaskCallbackTwo(name)
        }
        val r3 = GlobalScope.async {
            taskService.doTaskCallbackThree(name)
        }
        awaitAll(r1, r2, r3)

        return "${r1.getCompleted()} ${r2.getCompleted()} ${r3.getCompleted()}"
    }
}