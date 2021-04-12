package com.github.xsi640.springboot.example.interceptor

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport

@SpringBootApplication
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}

@Configuration
class MyWebAppConfigurer : WebMvcConfigurationSupport() {
    override fun addInterceptors(registry: InterceptorRegistry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        registry.addInterceptor(MyInterceptor1()).addPathPatterns("/**")
        registry.addInterceptor(MyInterceptor2()).addPathPatterns("/**")
        super.addInterceptors(registry)
    }
}