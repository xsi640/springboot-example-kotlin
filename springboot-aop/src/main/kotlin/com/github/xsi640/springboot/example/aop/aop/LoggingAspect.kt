package com.github.xsi640.springboot.example.aop.aop

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Aspect
@Component
class LoggingAspect {

    private val log = LoggerFactory.getLogger(LoggingAspect::class.java)

    @Around("@within(com.github.xsi640.springboot.example.aop.aop.Logging) || @annotation(com.github.xsi640.springboot.example.aop.aop.Logging)")
    fun doLog(point: ProceedingJoinPoint): Any {
        log.info("entering {}", point.signature)
        try {
            return point.proceed()
        } finally {
            log.info("exiting {}", point.signature);
        }
    }
}