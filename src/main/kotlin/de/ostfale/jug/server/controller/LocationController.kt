package de.ostfale.jug.server.controller

import de.ostfale.jug.server.converter.toDTO
import de.ostfale.jug.server.converter.toDTOList
import de.ostfale.jug.server.converter.toLocation
import de.ostfale.jug.server.domain.Location
import de.ostfale.jug.server.domain.LocationDTO
import de.ostfale.jug.server.service.LocationService
import de.ostfale.jug.server.service.PersonService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/location", produces = [MediaType.APPLICATION_JSON_VALUE])
class LocationController(
    private val locationService: LocationService,
    private val personService: PersonService
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @GetMapping
    fun findAll(): ResponseEntity<List<LocationDTO>> {
        log.debug("GET list of Locations")
        return ResponseEntity(locationService.findAll().toDTOList(personService), HttpStatus.OK)
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<LocationDTO> {
        log.debug("GET location with id : $id")
        val locationDTO: LocationDTO = locationService.getById(id).toDTO(personService)
        return ResponseEntity(locationDTO, HttpStatus.OK)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody locationDTO: LocationDTO): Location {
        log.debug("Create location with name ${locationDTO.name}")
        val location: Location = locationDTO.toLocation()
        return locationService.save(location)
    }

    @PutMapping("/{id}")
    fun update(@RequestBody locationDTO: LocationDTO, @PathVariable id: Long): Location {
        log.debug("Update location with id: $id")
        return locationService.update(locationDTO.toLocation(), id)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Long) {
        locationService.deleteById(id)
    }
}