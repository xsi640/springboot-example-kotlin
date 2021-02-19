package com.github.xsi640.springboot.example.jpa.controller

import com.github.xsi640.springboot.example.jpa.domain.Person
import com.github.xsi640.springboot.example.jpa.repository.PersonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/person")
class PersonController(
    @Autowired
    val personRepository: PersonRepository
) {
    @RequestMapping(value = ["{id}"], method = [RequestMethod.GET])
    fun findOne(@PathVariable("id") id: Int): Person {
        return personRepository.getOne(id)
    }

    @RequestMapping(method = [RequestMethod.GET])
    fun findAll(): List<Person> {
        return personRepository.findAll()
    }

    @RequestMapping(method = [RequestMethod.PUT])
    fun update(@RequestBody person: Person): Person {
        return personRepository.saveAndFlush(person)
    }

    @RequestMapping(method = [RequestMethod.POST, RequestMethod.PUT])
    fun create(@RequestBody person: Person): Person {
        return personRepository.saveAndFlush(person)
    }

    @RequestMapping(value = ["{id}"], method = [RequestMethod.DELETE])
    fun delete(@PathVariable("id") id: Int) {
        personRepository.deleteById(id)
    }
}