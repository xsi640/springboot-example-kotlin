package com.github.xsi640.springboot.example.jpa.domain

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "test")
data class Person(
    @Id
    var id: Int,
    @Column(nullable = false)
    var name: String,
    var age: Int,
    var birthday: Date
)