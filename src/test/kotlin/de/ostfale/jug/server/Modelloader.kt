package de.ostfale.jug.server

import Person
import de.ostfale.jug.server.domain.*
import java.time.LocalDateTime

object CreatePersonModel {
    fun create(
        anId: Long = 10,
        aFirstName: String = "Max",
        aLastName: String = "Schneider",
        anEmail: String = "mschneider@mail.de",
        aPhone: String = "0177 233455",
        aBio: String = "This is a bio....",
    ): Person {
        return Person(
            id = anId,
            firstName = aFirstName,
            lastName = aLastName,
            email = anEmail,
            phone = aPhone,
            bio = aBio
        )
    }
}

object CreatePersonList {
    fun create(): List<Person> {
        val anna = CreatePersonModel.create()
        val moritz = CreatePersonModel.create(
            anId = 11,
            aFirstName = "Moritz",
            aLastName = "Weber",
            aPhone = "0177 554433",
            anEmail = "mweber@mail.de"
        )
        return listOf(anna, moritz)
    }
}

object CreateLocationModel {
    fun create(
        anId: Long = 20L,
        aName: String = "Academic Work",
        aCountry: String = "DEU",
        aCity: String = "Hamburg",
        aPostalCode: String = "20457",
        aStreetName: String = "Großer Burstah",
        aStreetNumber: String = "50-52",
        aContactId: Long = 1,
        aRoomsList: Set<Room> = mutableSetOf(Room(name = "Meeting 1", capacity = 50))
    ): Location {
        return Location(
            id = anId,
            name = aName,
            country = aCountry,
            city = aCity,
            postalCode = aPostalCode,
            streetName = aStreetName,
            streetNumber = aStreetNumber,
            contactId = aContactId,
            rooms = aRoomsList
        )
    }
}

object CreateLocationDto {
    fun create(
        anId: Long = 21L,
        aName: String = "Academic Work",
        aCountry: String = "DEU",
        aCity: String = "Hamburg",
        aPostalCode: String = "20457",
        aStreetName: String = "Großer Burstah",
        aStreetNumber: String = "50-52",
        aContact: Person = CreatePersonModel.create(),
        aRoomsList: Set<Room> = mutableSetOf(Room(name = "Meeting 1", capacity = 50))
    ): LocationDTO {
        return LocationDTO(
            id = anId,
            name = aName,
            country = aCountry,
            city = aCity,
            postalCode = aPostalCode,
            streetName = aStreetName,
            streetNumber = aStreetNumber,
            contact = aContact,
            rooms = aRoomsList
        )
    }
}

object CreateLocationList {
    fun create(): List<Location> {
        val academic = CreateLocationModel.create()
        val haw = CreateLocationModel.create(
            aName = "HAW",
            aPostalCode = "20099",
            aStreetName = "Berliner Tor",
            aStreetNumber = "5",
            aContactId = 2L
        )
        return listOf(academic, haw)
    }
}

object CreateEventList {
    fun create(): List<Event> {
        val kotlin = CreateEventModel.create()
        val java = CreateEventModel.create(
            anId = 31,
            aTitle = "Java",
            aSpeakerId = 11
        )
        return listOf(kotlin, java)
    }
}

object CreateEventModel {
    fun create(
        anId: Long = 30L,
        aTitle: String = "Kotlin",
        aContent: String = "A short history of Kotlin",
        aRemark: String = "jvm language",
        aDateTime: LocalDateTime = LocalDateTime.now(),
        anOnlineEvent: Boolean = false,
        isCompleteEvent: Boolean = true,
        anEventStatus: EventStatus = EventStatus.PLANNED,
        aLocationId: Long = 20,
        aSpeakerId: Long = 10,
        aHistory: MutableList<Note> = mutableListOf(CreateNote.create())
    ): Event {
        return Event(
            id = anId,
            title = aTitle,
            content = aContent,
            remark = aRemark,
            dateTime = aDateTime,
            isOnlineEvent = anOnlineEvent,
            isComplete = isCompleteEvent,
            eventStatus = anEventStatus,
            locationId = aLocationId,
            speakerId = aSpeakerId,
            history = aHistory
        )
    }
}

object CreateEventDto {
    fun create(
        anId: Long = 30L,
        aTitle: String = "Kotlin",
        aContent: String = "A short history of Kotlin",
        aRemark: String = "jvm language",
        aDateTime: LocalDateTime = LocalDateTime.now(),
        anOnlineEvent: Boolean = false,
        isCompleteEvent: Boolean = true,
        anEventStatus: EventStatus = EventStatus.PLANNED,
        aLocation: Location = CreateLocationModel.create(),
        aSpeaker: Person = CreatePersonModel.create(),
        aHistory: MutableList<Note> = mutableListOf(CreateNote.create())
    ): EventDTO {
        return EventDTO(
            id = anId,
            title = aTitle,
            content = aContent,
            remark = aRemark,
            dateTime = aDateTime,
            isOnlineEvent = anOnlineEvent,
            isComplete = isCompleteEvent,
            eventStatus = anEventStatus,
            location = aLocation,
            speaker = aSpeaker,
            history = aHistory
        )
    }
}

object CreateNote {
    fun create(
        aTimeStamp: LocalDateTime = LocalDateTime.now(),
        aContent: String = "Initial Planning"
    ): Note {
        return Note(
            timestamp = aTimeStamp,
            content = aContent
        )
    }
}