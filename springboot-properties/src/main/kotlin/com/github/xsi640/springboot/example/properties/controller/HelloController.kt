package com.github.xsi640.springboot.example.properties.controller

import com.github.xsi640.springboot.example.properties.settings.SettingsAppYaml
import com.github.xsi640.springboot.example.properties.settings.SettingsProp
import com.github.xsi640.springboot.example.properties.settings.SettingsYaml
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController {
    @Autowired
    private lateinit var settingsAppYaml: SettingsAppYaml

    @Autowired
    private lateinit var settingsProp: SettingsProp

    @Autowired
    private lateinit var settingsYaml: SettingsYaml

    @GetMapping("/test")
    fun test(): String {
        return "name: ${settingsAppYaml.name} gender: ${settingsAppYaml.gender} ${settingsAppYaml.name2}"
    }

    @GetMapping("/test2")
    fun test2(): String {
        return "name: ${settingsProp.name} gender: ${settingsProp.age}"
    }

    @GetMapping("/test3")
    fun test3(): String {
        return "name: ${settingsYaml.name} gender:${settingsYaml.age}"
    }

    @GetMapping("/test4")
    fun test4(): String? {
        val builder = StringBuilder()
        settingsAppYaml.maps.forEach { (k, v) ->
            builder.append(k).append(":").append(v).append("<br />")
        }
        return builder.toString()
    }
}