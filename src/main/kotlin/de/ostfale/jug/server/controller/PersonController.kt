package de.ostfale.jug.server.controller

import Person
import de.ostfale.jug.server.service.PersonService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/v1/person")
class PersonController(private val personService: PersonService) {

    private val log = LoggerFactory.getLogger(javaClass)

    @GetMapping("/")
    fun findAll(): ResponseEntity<List<Person>> {
        log.debug("GET list of Persons")
        return ResponseEntity(personService.findAll(), HttpStatus.OK)
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<Person> {
        log.debug("GET person with id : $id")
        return ResponseEntity(personService.getById(id), HttpStatus.OK)
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody person: Person): Person {
        log.debug("Person with name ${person.firstName} ${person.lastName} is going to be created")
        return personService.save(person)
    }

    @PutMapping("/{id}")
    fun update(@RequestBody person: Person, @PathVariable id: Long): Person {
        log.debug("Update person with id: $id")
        return personService.update(person, id)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Long) {
        personService.deleteById(id)
    }
}