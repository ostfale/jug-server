package de.ostfale.jug.server.service

import Person
import de.ostfale.jug.server.exceptions.PersonNotFoundException
import de.ostfale.jug.server.repository.PersonRepository
import org.springframework.stereotype.Service

@Service
class PersonService(val personRepository: PersonRepository) {

    fun findAll(): List<Person> = personRepository.findAll().toList()

    fun getById(id: Long): Person = personRepository.findById(id).orElseThrow { PersonNotFoundException(id) }

    fun deleteById(id: Long) = personRepository.deleteById(id)

    fun save(person: Person): Person = personRepository.save(person)

    fun update(updatedPerson: Person): Person {
        val personId = updatedPerson.id
        if (personId != null) {
            save(updatedPerson)
        }
        return updatedPerson
    }
}