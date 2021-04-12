package com.github.xsi640.springboot.example.aop.aop

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.slf4j.LoggerFactory
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.util.*

@Aspect
@Component
@Order(-5)
class WebLoggingAspect {

    private val log = LoggerFactory.getLogger(WebLoggingAspect::class.java)

    private val startTime = ThreadLocal<Long>()

    /**
     * 定义一个切入点
     *
     *
     * ~ 第1个 * 代表任意修饰符及任意返回值.
     * ~ 第2个 * 任意包名
     * ~ 第3个 * 代表任意方法.
     * ~ .. 匹配任意数量的参数.
     */
    @Pointcut("execution(public * com.github.xsi640.springboot.example.aop.controller.*.*(..))")
    fun webLog() {
    }

    /**
     * 切入方法之前
     *
     * @param joinPoint
     */
    @Before("webLog()")
    fun doBefore(joinPoint: JoinPoint) {
        startTime.set(System.currentTimeMillis())
        log.info("WebLoggingAspect.doBefore()")
        val attributes = RequestContextHolder.getRequestAttributes() as ServletRequestAttributes?
        val request = attributes!!.request

        // 记录下请求内容
        log.info("URL : " + request.requestURL.toString())
        log.info("HTTP_METHOD : " + request.method)
        log.info("IP : " + request.remoteAddr)
        log.info("CLASS_METHOD : " + joinPoint.signature.declaringTypeName + "." + joinPoint.signature.name)
        log.info("ARGS : " + Arrays.toString(joinPoint.args))
        val enus = request.parameterNames
        while (enus.hasMoreElements()) {
            val paraName = enus.nextElement()
            log.info(paraName + ":" + request.getParameter(paraName))
        }
    }

    /**
     * 切入方法之后
     *
     * @param joinPoint
     */
    @AfterReturning("webLog()")
    fun doAfterReturning(joinPoint: JoinPoint?) {
        // 处理完请求，返回内容
        log.info("WebLoggingAspect.doAfterReturning()")
        log.info("耗时（毫秒） : " + (System.currentTimeMillis() - startTime.get()))
    }
}