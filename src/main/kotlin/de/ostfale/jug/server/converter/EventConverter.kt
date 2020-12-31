package de.ostfale.jug.server.converter

import Person
import de.ostfale.jug.server.domain.Event
import de.ostfale.jug.server.domain.EventDTO
import de.ostfale.jug.server.domain.LocationDTO
import de.ostfale.jug.server.domain.PersonRef
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

    var eventLocation: LocationDTO? = null
    val locationID = this.locationId
    val speakerIDs: MutableSet<PersonRef> = this.speakerIds

    if (locationID != null) {
        val location = locationService.getById(locationID)
        eventLocation = location.toDTO(personService)
    }

    val persons = mutableSetOf<Person>()
    speakerIDs.forEach { personRef -> persons.add(personService.getById(personRef.person)) }

    return EventDTO(
        id = id,
        title = title,
        content = content,
        remark = remark,
        dateTime = dateTime,
        isOnlineEvent = isOnlineEvent,
        isComplete = isComplete,
        eventStatus = eventStatus,
        locationStatus = locationStatus,
        scheduleStatus = scheduleStatus,
        location = eventLocation,
        speaker = persons,
        history = history
    )
}

fun EventDTO.toEvent(): Event {

    val speakerRef = mutableSetOf<PersonRef>()
    this.speaker?.forEach { person ->
        val personId = person.id
        if (personId != null) {
            speakerRef.add(PersonRef(personId))
        }
    }

    return Event(
        id = id,
        title = title,
        content = content,
        remark = remark,
        dateTime = dateTime,
        isOnlineEvent = isOnlineEvent,
        eventStatus = eventStatus,
        locationStatus = locationStatus,
        scheduleStatus = scheduleStatus,
        locationId = this.location?.id,
        speakerIds = speakerRef,
        history = this.history
    )
}