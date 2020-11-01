package de.ostfale.jug.server.controller

import de.ostfale.jug.server.converter.toDTOList
import de.ostfale.jug.server.domain.EventDTO
import de.ostfale.jug.server.service.EventService
import de.ostfale.jug.server.service.LocationService
import de.ostfale.jug.server.service.PersonService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/event")
class EventController(
    private val eventService: EventService,
    private val personService: PersonService,
    private val locationService: LocationService,
) {
    private val log = LoggerFactory.getLogger(javaClass)

    @GetMapping("/")
    fun findAll(): ResponseEntity<List<EventDTO>> {
        log.debug("GET list of events")
        return ResponseEntity(eventService.findAll().toDTOList(personService, locationService), HttpStatus.OK)
    }
}