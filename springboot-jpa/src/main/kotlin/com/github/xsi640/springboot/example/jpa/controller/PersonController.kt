package com.github.xsi640.springboot.example.jpa.controller

import com.github.xsi640.springboot.example.jpa.domain.Person
import com.github.xsi640.springboot.example.jpa.repository.PersonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import java.util.*
import kotlin.random.Random

@RestController
@RequestMapping("/person")
class PersonController(
    @Autowired
    val personRepository: PersonRepository
) {
    @RequestMapping(value = ["{id}"], method = [RequestMethod.GET])
    suspend fun findOne(@PathVariable("id") id: Int): Person {
        return personRepository.getOne(id)
    }

    @RequestMapping(method = [RequestMethod.GET])
    suspend fun findAll(): List<Person> {
        return personRepository.findAll()
    }

    @RequestMapping(method = [RequestMethod.PUT])
    suspend fun update(@RequestBody person: Person): Person {
        return personRepository.saveAndFlush(person)
    }

    @RequestMapping(method = [RequestMethod.POST, RequestMethod.PUT])
    suspend fun create(@RequestBody person: Person): Person {
        return personRepository.saveAndFlush(person)
    }

    @RequestMapping(value = ["{id}"], method = [RequestMethod.DELETE])
    suspend fun delete(@PathVariable("id") id: Int) {
        personRepository.deleteById(id)
    }
}