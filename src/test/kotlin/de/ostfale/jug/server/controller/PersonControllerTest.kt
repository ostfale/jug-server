package de.ostfale.jug.server.controller

import Person
import com.ninjasquad.springmockk.MockkBean
import de.ostfale.jug.server.service.PersonService
import io.mockk.every
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@Tag("unitTest")
@WebMvcTest
@DisplayName("Test REST access for person controller")
internal class PersonControllerTest(@Autowired val mockMvc: MockMvc) {

    @MockkBean
    lateinit var personService: PersonService

    @Test
    @DisplayName("Read all persons per REST from DB")
    fun `Get all persons from database`() {
        // given
        val persons = createPersons()
        // when
        every { personService.findAll() } returns persons
        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/person/").accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.[0].firstName").value("Max"))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.[1].firstName").value("Moritz"))
    }

    private fun createPersons(): List<Person> {
        val max = Person(firstName = "Max", lastName = "Schneider", email = "mschneider@mail.de", phone = "0177 233455")
        val moritz = Person(firstName = "Moritz", lastName = "Weber", email = "mweber@mail.de", phone = "0177 554433")
        return listOf(max, moritz)
    }
}
