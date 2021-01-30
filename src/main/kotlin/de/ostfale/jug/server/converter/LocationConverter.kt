package de.ostfale.jug.server.converter

import de.ostfale.jug.server.domain.Location
import de.ostfale.jug.server.domain.LocationDTO
import de.ostfale.jug.server.service.PersonService

fun List<Location>.toDTOList(personsService: PersonService): List<LocationDTO> {
    val dtoList = mutableListOf<LocationDTO>()
    this.forEach { dtoList.add(it.toDTO(personsService)) }
    return dtoList
}

fun Location.toDTO(personsService: PersonService): LocationDTO {
    return LocationDTO(
        id = this.id,
        name = this.name,
        country = this.country,
        city = this.city,
        postalCode = this.postalCode,
        streetName = this.streetName,
        streetNumber = this.streetNumber,
        rooms = this.rooms,
        contact = this.contactId?.let { personsService.getById(it) }
    )
}

fun LocationDTO.toLocation(): Location {
    return Location(
        id = this.id,
        name = this.name,
        country = this.country,
        city = this.city,
        postalCode = this.postalCode,
        streetName = this.streetName,
        streetNumber = this.streetNumber,
        rooms = this.rooms,
        contactId = this.contact?.id
    )
}