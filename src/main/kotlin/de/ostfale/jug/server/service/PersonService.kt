package de.ostfale.jug.server.service

import Person
import de.ostfale.jug.server.repository.PersonRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class PersonService(val personRepository: PersonRepository) {

    private val log = LoggerFactory.getLogger(javaClass)

    fun findAll(): List<Person> = personRepository.findAll().toList()

}