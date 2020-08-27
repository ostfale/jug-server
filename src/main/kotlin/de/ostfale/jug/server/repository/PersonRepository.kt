package de.ostfale.jug.server.repository

import Person
import org.springframework.data.repository.CrudRepository

interface PersonRepository : CrudRepository<Person, Long>