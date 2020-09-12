package de.ostfale.jug.server.service

import de.ostfale.jug.server.domain.Location
import de.ostfale.jug.server.exceptions.LocationNotFoundException
import de.ostfale.jug.server.repository.LocationRepository
import org.springframework.stereotype.Service

@Service
class LocationService(val locationRepository: LocationRepository) {

    fun findAll(): List<Location> = locationRepository.findAll().toList()

    fun getById(id: Long): Location = locationRepository.findById(id).orElseThrow { LocationNotFoundException(id) }

    fun deleteById(id: Long) = locationRepository.deleteById(id)

    fun save(person: Location): Location = locationRepository.save(person)

}