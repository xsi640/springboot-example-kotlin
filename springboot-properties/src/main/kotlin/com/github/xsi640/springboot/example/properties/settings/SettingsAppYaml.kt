package com.github.xsi640.springboot.example.properties.settings

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "test")
data class SettingsAppYaml(
    var name: String = "",
    var gender: String = "",
    var maps: Map<String, String> = mapOf(),
    var name2: String = ""
)