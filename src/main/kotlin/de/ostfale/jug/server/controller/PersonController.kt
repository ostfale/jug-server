package de.ostfale.jug.server.controller

import Person
import de.ostfale.jug.server.service.PersonService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/v1/person")
class PersonController(val personService: PersonService) {

    private val log = LoggerFactory.getLogger(javaClass)

    @GetMapping("/")
    fun findAll(): ResponseEntity<List<Person>> {
        return ResponseEntity(personService.findAll(), HttpStatus.OK)
    }

}