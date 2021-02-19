package com.github.xsi640.springboot.example.jdbc

import com.github.xsi640.springboot.example.jdbc.controller.PersonController
import com.github.xsi640.springboot.example.jdbc.dao.PersonDao
import com.github.xsi640.springboot.example.jdbc.domain.Person
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.util.Assert
import java.util.*

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@SpringBootTest
class ApplicationTest(
    @Autowired
    val personController: PersonController,
    @Autowired
    val personDao: PersonDao
) {

    companion object {
        var id = 0
    }

    @Order(0)
    @Test
    fun clear() {
        personDao.clear()
    }

    @Order(1)
    @Test
    fun create() {
        val p = Person(1, "zhangsan", 20, Date())
        val inserted = personController.create(p)
        Assert.notNull(inserted, "error")
        id = inserted.id
    }

    @Order(2)
    @Test
    fun get() {
        val p = personController.findOne(id)
        Assert.notNull(p, "error")
    }

    @Order(3)
    @Test
    fun update() {
        val p = personController.findOne(id)
        p!!.name = "lisi"
        personController.update(p)
        val existsPerson = personController.findOne(id)
        Assert.isTrue(existsPerson!!.name == "lisi", "error")
    }

    @Order(4)
    @Test
    fun findAll() {
        val persons = personController.findAll()
        Assert.isTrue(persons.size == 1, "error")
    }

    @Order(5)
    @Test
    fun delete() {
        personController.delete(id)
        Assert.isTrue(personController.findAll().isEmpty(), "error")
    }
}