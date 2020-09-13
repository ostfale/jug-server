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

    fun update(updateLocation: Location, id: Long): Location {
        val savedLocation = getById(id)
        savedLocation.apply {
            name = updateLocation.name
            country = updateLocation.country
            city = updateLocation.city
            postalCode = updateLocation.postalCode
            streetName = updateLocation.streetName
            streetNumber = updateLocation.streetNumber
            contactId = updateLocation.contactId
            rooms = updateLocation.rooms
        }
        return save(savedLocation)
    }
}