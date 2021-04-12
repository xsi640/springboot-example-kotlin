package com.github.xsi640.springboot.example.async.service.impl

import com.github.xsi640.springboot.example.async.service.TaskService
import kotlinx.coroutines.delay
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class TaskServiceImpl : TaskService {
    private val log = LoggerFactory.getLogger(TaskServiceImpl::class.java)

    override fun doTaskOne() {
        log.info("开始做任务一")
        val start = System.currentTimeMillis()
        Thread.sleep(Random.nextInt(10000).toLong())
        val end = System.currentTimeMillis()
        log.info("完成任务一，耗时：" + (end - start) + "毫秒")
    }

    override fun doTaskTwo() {
        log.info("开始做任务二")
        val start = System.currentTimeMillis()
        Thread.sleep(Random.nextInt(10000).toLong())
        val end = System.currentTimeMillis()
        log.info("完成任务二，耗时：" + (end - start) + "毫秒")
    }

    override fun doTaskThree() {
        log.info("开始做任务三")
        val start = System.currentTimeMillis()
        Thread.sleep(Random.nextInt(10000).toLong())
        val end = System.currentTimeMillis()
        log.info("完成任务三，耗时：" + (end - start) + "毫秒")
    }

    override suspend fun doTaskCallbackOne(name: String): String {
        log.info("开始做任务一（带回调）")
        val start = System.currentTimeMillis()
        delay(Random.nextInt(10000).toLong())
        val end = System.currentTimeMillis()
        log.info("完成任务一（带回调），耗时：" + (end - start) + "毫秒")
        return "hello, $name"
    }

    override suspend fun doTaskCallbackTwo(name: String): String {
        log.info("开始做任务二（带回调）")
        val start = System.currentTimeMillis()
        delay(Random.nextInt(10000).toLong())
        val end = System.currentTimeMillis()
        log.info("完成任务二（带回调），耗时：" + (end - start) + "毫秒")
        return "hello, $name"
    }

    override suspend fun doTaskCallbackThree(name: String): String {
        log.info("开始做任务三（带回调）")
        val start = System.currentTimeMillis()
        delay(Random.nextInt(10000).toLong())
        val end = System.currentTimeMillis()
        log.info("完成任务三（带回调），耗时：" + (end - start) + "毫秒")
        return "hello, $name"
    }
}