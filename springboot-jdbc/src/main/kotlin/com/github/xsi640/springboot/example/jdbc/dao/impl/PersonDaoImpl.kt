package com.github.xsi640.springboot.example.jdbc.dao.impl

import com.github.xsi640.springboot.example.jdbc.dao.PersonDao
import com.github.xsi640.springboot.example.jdbc.domain.Person
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.PreparedStatementCreator
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository
import java.sql.Date
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Statement

@Repository
class PersonDaoImpl : PersonDao {
    private val GET = "SELECT * FROM person WHERE id=?"
    private val INSERT = "INSERT INTO person(name, age, birthday) VALUES(?,?,?)"
    private val UPDATE = "UPDATE person SET name=?, age=?, birthday=? WHERE id=?"
    private val DELETE = "DELETE FROM person WHERE id=?"
    private val SELECT_ALL = "SELECT * FROM person"
    private val DELETE_ALL = "DELETE FROM person"

    @Autowired
    private lateinit var jdbcTemplate: JdbcTemplate

    override fun get(id: Int): Person {
        return jdbcTemplate.queryForObject(GET, { rs, _ ->
            Person(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getInt("age"),
                java.util.Date(rs.getDate("birthday").time)
            )
        }, id)!!
    }

    override fun insert(person: Person): Person {
        val keyHolder = GeneratedKeyHolder()
        if (jdbcTemplate.update(PreparedStatementCreator { con ->
                val preparedStatement = con.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)
                preparedStatement.setString(1, person.name)
                preparedStatement.setInt(2, person.age)
                preparedStatement.setDate(3, Date(person.birthday.time))
                preparedStatement
            }, keyHolder) > 0) {
            person.id = keyHolder.key!!.toInt()
        }
        return person
    }

    override fun update(person: Person): Person {
        jdbcTemplate.update(UPDATE, person.name, person.age, person.birthday, person.id)
        return person
    }

    override fun delete(id: Int): Int {
        return jdbcTemplate.update(DELETE, id)
    }

    override fun selectAll(): MutableList<Person> {
        return jdbcTemplate.query(SELECT_ALL) { rs, _ ->
            Person(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getInt("age"),
                java.util.Date(rs.getDate("birthday").time)
            )
        }
    }

    override fun clear(): Int {
        return jdbcTemplate.update(DELETE_ALL)
    }
}