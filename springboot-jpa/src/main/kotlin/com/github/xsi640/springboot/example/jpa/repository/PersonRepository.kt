package com.github.xsi640.springboot.example.jpa.repository

import com.github.xsi640.springboot.example.jpa.domain.Person
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PersonRepository : JpaRepository<Person, Int> {
}