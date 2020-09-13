package de.ostfale.jug.server.service

import Person
import com.ninjasquad.springmockk.MockkBean
import de.ostfale.jug.server.CreatePersonList
import de.ostfale.jug.server.CreatePersonModel
import de.ostfale.jug.server.exceptions.PersonNotFoundException
import de.ostfale.jug.server.repository.PersonRepository
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.verify
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*

@Tag("unitTest")
@DisplayName("Perform CRUD operations for Person service")
@ExtendWith(SpringExtension::class)
internal class PersonServiceTest {

    @MockkBean
    lateinit var personRepository: PersonRepository

    private lateinit var personService: PersonService

    @BeforeEach
    fun configureSUT() {
        personService = PersonService(personRepository)
    }

    @Test
    @DisplayName("Service calls delete person method of repo")
    fun `Verify that repository delete method is called by service`() {
        // given
        every { personRepository.deleteById(any()) } just Runs
        // when
        personService.deleteById(1L)
        // then
        verify { personRepository.deleteById(1L) }
    }

    @Test
    @DisplayName("Retrieve list with all persons")
    fun `Find all persons in a repository`() {
        // given
        val persons: Iterable<Person> = CreatePersonList.create()
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
        val persons: Iterable<Person> = CreatePersonList.create()
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
        val firstPerson: Person = CreatePersonModel.create()
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
        val personList = CreatePersonList.create()
        val max = personList.first()
        max.lastName = "Schubert"
        // when
        every { personRepository.findById(any()) } returns Optional.of(max)
        every { personRepository.save(any()) } returns max
        val updatedPerson = personService.update(max, 1L)
        // then
        assertEquals("Schubert", updatedPerson.lastName)
    }
}