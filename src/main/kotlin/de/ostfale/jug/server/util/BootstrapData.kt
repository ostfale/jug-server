package de.ostfale.jug.server.util

import Person
import de.ostfale.jug.server.domain.*
import de.ostfale.jug.server.repository.EventRepository
import de.ostfale.jug.server.repository.LocationRepository
import de.ostfale.jug.server.repository.PersonRepository
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.Month

@Component
@Profile("dev")
class BootstrapData(
    val personRepository: PersonRepository,
    val locationRepository: LocationRepository,
    val eventRepository: EventRepository
) : CommandLineRunner {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun run(vararg args: String?) {
        createData()
    }

    private fun createData() {
        log.debug("Bootstrap: create some persons...")
        val julia = Person(
            firstName = "Julia",
            lastName = "Paxmann",
            email = "julia.paxmann@academicwork.de",
            phone = "0162 10 32 958"
        )
        val axel = Person(
            firstName = "Axel",
            lastName = "Schmolitzky",
            email = "axel.schmolitzky@haw-hamburg.de",
            phone = "040 / 42875-812"
        )
        val nina = Person(
            firstName = "Nina",
            lastName = "Blau",
            email = "nb@mail.com",
            phone = "0177 665544",
            bio = "My name is Nina..."
        )
        val lena = Person(
            firstName = "Lena",
            lastName = "Weiden",
            email = "lena@mail.com",
            phone = "0177 445544",
            bio = "My name is Lena..."
        )
        personRepository.save(julia)
        personRepository.save(axel)
        personRepository.save(nina)
        personRepository.save(lena)

        log.debug("Bootstrap: create some rooms...")
        val room1 = Room(name = "first room", capacity = 30)
        val room2 = Room(name = "second room", capacity = 60, remark = "This is the better solution")
        val roomHaw12 = Room(name = "Hörsaal 01.12", capacity = 120)
        val roomHaw13 = Room(name = "Hörsaal 01.13", capacity = 180)

        log.debug("Bootstrap: create some locations...")
        val academicWork = Location(
            name = "Academic Work",
            country = "DEU",
            city = "Hamburg",
            postalCode = "20457",
            streetName = "Großer Burstah",
            streetNumber = "50-52",
            contactId = julia.id!!,
            rooms = mutableSetOf(room1)
        )
        val haw = Location(
            name = "HAW",
            country = "DEU",
            city = "Hamburg",
            postalCode = "20099",
            streetName = "Berliner Tor",
            streetNumber = "5",
            contactId = axel.id!!,
            rooms = mutableSetOf(roomHaw12, roomHaw13)
        )
        locationRepository.save(academicWork)
        locationRepository.save(haw)

        val testEvent = Event(
            title = "Kotlin in a Nutshell",
            content = "Kotlin is a JVM Language and so on...",
            remark = "No remark yet!",
            dateTime = LocalDateTime.of(2020, Month.DECEMBER, 17, 19, 0),
            isOnlineEvent = false,
            isComplete = true,
            eventStatus = EventStatus.PLANNED,
            locationId = academicWork.id,
            speakerId = nina.id,
            history = mutableSetOf(Note(timestamp = LocalDateTime.now(), content = "First planning finished"))
        )
        eventRepository.save(testEvent)
    }
}