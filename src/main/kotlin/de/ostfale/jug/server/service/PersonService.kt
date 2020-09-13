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

    fun update(updatedPerson: Person, id: Long): Person {
        val savedPerson = getById(id)
        savedPerson.apply {
            firstName = updatedPerson.firstName
            lastName = updatedPerson.lastName
            email = updatedPerson.email
            phone = updatedPerson.phone
            bio = updatedPerson.bio
        }
        return save(savedPerson)
    }
}