package com.github.xsi640.springboot.example.properties.settings

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

@Configuration
@PropertySource("classpath:settings.properties")
@ConfigurationProperties(prefix = "prop")
data class SettingsProp(
    var name: String = "",
    var age: Int = 0
)