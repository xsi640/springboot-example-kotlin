package com.github.xsi640.springboot.example.async.service

interface TaskService {
    fun doTaskOne()
    fun doTaskTwo()
    fun doTaskThree()

    suspend fun doTaskCallbackOne(name: String): String
    suspend fun doTaskCallbackTwo(name: String): String
    suspend fun doTaskCallbackThree(name: String): String
}