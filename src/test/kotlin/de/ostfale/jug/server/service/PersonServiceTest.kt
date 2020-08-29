package de.ostfale.jug.server.service

import Person
import com.ninjasquad.springmockk.MockkBean
import de.ostfale.jug.server.exceptions.PersonNotFoundException
import de.ostfale.jug.server.repository.PersonRepository
import io.mockk.every
import io.mockk.slot
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*

@Tag("unitTest")
@DisplayName("Perform CRUD operations for Person service")
@ExtendWith(SpringExtension::class)
class PersonServiceTest {

    @MockkBean
    lateinit var personRepository: PersonRepository

    private lateinit var personService: PersonService

    @BeforeEach
    fun configureSUT() {
        personService = PersonService(personRepository)
    }

    @Test
    @DisplayName("Retrieve list with all persons")
    fun `Find all persons in a repository`() {
        // given
        val persons: Iterable<Person> = createPersons()
        // when
        every { personRepository.findAll() } returns persons
        val result = personService.findAll()
        // then
        assertEquals(2, result.size)
        assertEquals("Max", result[0].firstName)
        assertEquals("Moritz", result[1].firstName)
    }

    @Test
    @DisplayName("Retrieve a person by its Id")
    fun `Find a person by id`() {
        // given
        val persons: Iterable<Person> = createPersons()
        // when
        every { personRepository.findById(any()) } returns Optional.of(persons.first())
        val result = personService.getById(1)
        // then
        assertEquals("Max", result.firstName)
        assertEquals("Schneider", result.lastName)
        assertEquals("mschneider@mail.de", result.email)
        assertEquals("0177 233455", result.phone)
        assertEquals("This is a bio....", result.bio)
    }

    @Test
    @DisplayName("Retrieve non existing person by its Id")
    fun `Retrieving non existing person throws an PersonNotFoundException`() {
        // when
        every { personRepository.findById(any()) } throws PersonNotFoundException(0)
        assertThrows<PersonNotFoundException> {
            personService.getById(0)
        }
    }

    @Test
    @DisplayName("Save new person object")
    fun `Save person object`() {
        // given
        val firstPerson: Person = createPersons().first()
        // when
        every { personRepository.save(firstPerson) } returns firstPerson
        val savedPerson = personService.save(firstPerson)
        // then
        assertEquals("Max", savedPerson.firstName)
        assertEquals("Schneider", savedPerson.lastName)
        assertEquals("mschneider@mail.de", savedPerson.email)
        assertEquals("0177 233455", savedPerson.phone)
        assertEquals("This is a bio....", savedPerson.bio)
    }

    @Test
    @DisplayName("Update attributes of existing person")
    fun `Update an existing person`() {
        // given
        val personList = createPersons()
        val moritz = personList.last()
        moritz.id = 1
        // when
        val slot = slot<Person>()
     //   every { personService.save(capture(slot)) } returns slot.captured
        every {personRepository.save(any())} returns moritz
        val updatedPerson = personService.update(moritz)
        // then
        assertEquals(1, updatedPerson.id)
        assertEquals("Moritz", updatedPerson.firstName)
    }

    private fun createPersons(): Iterable<Person> {
        val max = Person(
            id = 1,
            firstName = "Max",
            lastName = "Schneider",
            email = "mschneider@mail.de",
            phone = "0177 233455",
            bio = "This is a bio...."
        )
        val moritz = Person(
            id = 2,
            firstName = "Moritz",
            lastName = "Weber",
            email = "mweber@mail.de",
            phone = "0177 554433"
        )
        return listOf(max, moritz)
    }
}