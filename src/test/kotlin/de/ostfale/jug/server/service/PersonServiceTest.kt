package de.ostfale.jug.server.service

import Person
import com.ninjasquad.springmockk.MockkBean
import de.ostfale.jug.server.repository.PersonRepository
import io.mockk.every
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.junit.jupiter.SpringExtension

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

    private fun createPersons(): Iterable<Person> {
        val max = Person(firstName = "Max", lastName = "Schneider", email = "mschneider@mail.de", phone = "0177 233455")
        val moritz = Person(firstName = "Moritz", lastName = "Weber", email = "mweber@mail.de", phone = "0177 554433")
        return listOf(max, moritz)
    }
}