package com.github.xsi640.springboot.example.invocation

import org.springframework.context.annotation.Import

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
@Import(WebServiceRegistrar::class)
annotation class EnableWebService(
    vararg val basePackages: String = []
)

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class WebService(
    val name: String = ""
)