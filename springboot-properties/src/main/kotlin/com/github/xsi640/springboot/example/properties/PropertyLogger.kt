package com.github.xsi640.springboot.example.properties

import org.slf4j.LoggerFactory
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.core.env.AbstractEnvironment
import org.springframework.core.env.EnumerablePropertySource
import org.springframework.stereotype.Component

@Component
class PropertyLogger {

    private val log = LoggerFactory.getLogger(PropertyLogger::class.java)

    @EventListener
    fun handleContextRefresh(event: ContextRefreshedEvent) {
        val env = event.applicationContext.environment
        log.info("====== Environment and configuration ======")
        log.info("Active profiles: {}", env.activeProfiles.contentToString())
        val sources = (env as AbstractEnvironment).propertySources
        sources.filter { ps -> ps is EnumerablePropertySource<*> }
            .map { p -> (p as EnumerablePropertySource).propertyNames }
            .forEach { names ->
                names.forEach { name ->
                    log.info("$name: ${env.getProperty(name)}")
                }
            }
        log.info("===========================================")
    }
}
