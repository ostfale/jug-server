package de.ostfale.jug.server.controller

import de.ostfale.jug.server.converter.toDTO
import de.ostfale.jug.server.converter.toDTOList
import de.ostfale.jug.server.converter.toEvent
import de.ostfale.jug.server.converter.toLocation
import de.ostfale.jug.server.domain.Event
import de.ostfale.jug.server.domain.EventDTO
import de.ostfale.jug.server.domain.Location
import de.ostfale.jug.server.domain.LocationDTO
import de.ostfale.jug.server.service.EventService
import de.ostfale.jug.server.service.LocationService
import de.ostfale.jug.server.service.PersonService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

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

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<EventDTO> {
        log.debug("GET event with id : $id")
        val eventDTO: EventDTO = eventService.getById(id).toDTO(personService, locationService)
        return ResponseEntity(eventDTO, HttpStatus.OK)
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody eventDTO: EventDTO): Event {
        log.debug("Create event with name ${eventDTO.title} ")
        val event: Event = eventDTO.toEvent()
        return eventService.save(event)
    }

    @PutMapping("/{id}")
    fun update(@RequestBody eventDTO: EventDTO, @PathVariable id: Long): Event {
        log.debug("Update location with id: $id")
        return eventService.save(eventDTO.toEvent())
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) {
        eventService.deleteById(id)
    }
}