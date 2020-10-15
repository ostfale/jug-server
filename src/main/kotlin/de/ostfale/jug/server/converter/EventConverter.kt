package de.ostfale.jug.server.converter

import Person
import de.ostfale.jug.server.domain.Event
import de.ostfale.jug.server.domain.EventDTO
import de.ostfale.jug.server.domain.Location
import de.ostfale.jug.server.service.LocationService
import de.ostfale.jug.server.service.PersonService

fun List<Event>.toDTOList(
    personsService: PersonService,
    locationService: LocationService
): List<EventDTO> {
    val dtoList = mutableListOf<EventDTO>()
    this.forEach { dtoList.add(it.toDTO(personsService, locationService)) }
    return dtoList
}

fun Event.toDTO(
    personService: PersonService,
    locationService: LocationService
): EventDTO {

    var eventLocation: Location? = null
    var eventSpeaker: Person? = null

    val locationID = this.locationId
    val speakerID = this.speakerId

    if (locationID != null) {
        eventLocation = locationService.getById(locationID)
    }

    if (speakerID != null) {
        eventSpeaker = personService.getById(speakerID)
    }

    return EventDTO(
        id = id,
        title = title,
        content = content,
        remark = remark,
        dateTime = dateTime,
        isOnlineEvent = isOnlineEvent,
        isComplete = isComplete,
        eventStatus = eventStatus,
        location = eventLocation,
        speaker = eventSpeaker,
        history = history
    )
}

fun EventDTO.toEvent(): Event {
    return Event(
        id = id,
        title = title,
        content = content,
        remark = remark,
        dateTime = dateTime,
        isOnlineEvent = isOnlineEvent,
        eventStatus = eventStatus,
        locationId = this.location?.id,
        speakerId = this.speaker?.id,
        history = this.history
    )
}