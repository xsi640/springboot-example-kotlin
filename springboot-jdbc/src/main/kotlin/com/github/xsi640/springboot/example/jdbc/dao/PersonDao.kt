package com.github.xsi640.springboot.example.jdbc.dao

import com.github.xsi640.springboot.example.jdbc.domain.Person

interface PersonDao {
    fun get(id: Int): Person
    fun insert(person: Person): Person
    fun update(person: Person): Person
    fun delete(id: Int): Int
    fun selectAll(): MutableList<Person>
    fun clear(): Int
}