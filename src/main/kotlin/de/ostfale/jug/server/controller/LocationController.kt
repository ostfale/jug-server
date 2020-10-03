package de.ostfale.jug.server.controller

import de.ostfale.jug.server.converter.toDTOList
import de.ostfale.jug.server.domain.LocationDTO
import de.ostfale.jug.server.service.LocationService
import de.ostfale.jug.server.service.PersonService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/location")
class LocationController(
    val locationService: LocationService,
    val personService: PersonService
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @GetMapping("/")
    fun findAll(): ResponseEntity<List<LocationDTO>> {
        log.debug("GET list of Locations")
        return ResponseEntity(locationService.findAll().toDTOList(personService), HttpStatus.OK)
    }

}