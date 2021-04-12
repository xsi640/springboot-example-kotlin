package com.github.xsi640.springboot.example.interceptor

import org.slf4j.LoggerFactory
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class MyInterceptor1 : HandlerInterceptor {
    private val log = LoggerFactory.getLogger(MyInterceptor1::class.java)

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        log.info("MyInterceptor1 -> 在请求处理之前进行调用（Controller方法调用之前）")
        return true
    }

    override fun postHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        modelAndView: ModelAndView?
    ) {
        log.info("MyInterceptor1 -> 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）")
    }

    override fun afterCompletion(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        ex: Exception?
    ) {
        log.info("MyInterceptor1 -> 在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）")
    }
}

class MyInterceptor2 : HandlerInterceptor {
    private val log = LoggerFactory.getLogger(MyInterceptor2::class.java)

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        log.info("MyInterceptor2 -> 在请求处理之前进行调用（Controller方法调用之前）")
        return true
    }

    override fun postHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        modelAndView: ModelAndView?
    ) {
        log.info("MyInterceptor2 -> 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）")
    }

    override fun afterCompletion(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        ex: Exception?
    ) {
        log.info("MyInterceptor2 -> 在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）")
    }
}
