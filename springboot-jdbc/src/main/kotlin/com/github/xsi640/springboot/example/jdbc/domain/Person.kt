package com.github.xsi640.springboot.example.jdbc.domain

import java.util.*

data class Person(
    var id: Int,
    var name: String,
    var age: Int,
    var birthday: Date
)