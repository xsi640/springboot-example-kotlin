package com.github.xsi640.springboot.example.jdbc.controller

import com.github.xsi640.springboot.example.jdbc.dao.PersonDao
import com.github.xsi640.springboot.example.jdbc.domain.Person
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/person")
class PersonController(
    @Autowired
    val personDao: PersonDao
) {
    @RequestMapping(value = ["{id}"], method = [RequestMethod.GET])
    fun findOne(@PathVariable("id") id: Int): Person? {
        return personDao.get(id)
    }

    @RequestMapping(method = [RequestMethod.GET])
    fun findAll(): List<Person> {
        return personDao.selectAll()
    }

    @RequestMapping(method = [RequestMethod.PUT])
    fun update(@RequestBody person: Person): Person {
        return personDao.update(person)
    }

    @RequestMapping(method = [RequestMethod.POST, RequestMethod.PUT])
    fun create(@RequestBody person: Person): Person {
        return personDao.insert(person)
    }

    @RequestMapping(value = ["{id}"], method = [RequestMethod.DELETE])
    fun delete(@PathVariable("id") id: Int) {
        personDao.delete(id)
    }
}