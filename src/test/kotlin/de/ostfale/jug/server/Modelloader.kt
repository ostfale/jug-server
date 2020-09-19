package de.ostfale.jug.server

import Person
import de.ostfale.jug.server.domain.Location
import de.ostfale.jug.server.domain.LocationDTO
import de.ostfale.jug.server.domain.Room

object CreatePersonModel {
    fun create(
        anId: Long = 1,
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
            anId = 2,
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
        anId: Long = 1L,
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
        anId: Long = 1L,
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