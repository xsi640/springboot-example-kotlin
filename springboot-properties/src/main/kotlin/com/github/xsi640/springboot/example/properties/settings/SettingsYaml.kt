package com.github.xsi640.springboot.example.properties.settings

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

@Configuration
@PropertySource(value = ["classpath:settings.yml"], factory = YamlPropertyLoaderFactory::class)
@ConfigurationProperties(prefix = "settings")
data class SettingsYaml(
    var name: String = "",
    var age: Int = 0
)