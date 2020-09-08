package de.ostfale.jug.server.controller

import com.ninjasquad.springmockk.MockkBean
import de.ostfale.jug.server.CreatePersonList
import de.ostfale.jug.server.CreatePersonModel
import de.ostfale.jug.server.service.PersonService
import io.mockk.every
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
        val persons = CreatePersonList.create()
        // when
        every { personService.findAll() } returns persons
        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/person/").accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.[0].firstName").value("Max"))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.[1].firstName").value("Moritz"))
    }

    @Test
    @DisplayName("Read a person with an existing ID from DB")
    fun `Read an existing person from database`() {
        // given
        val dbPerson = CreatePersonModel.create()
        // when
        every { personService.getById(any()) } returns dbPerson
        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/person/1").accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.firstName").value("Max"))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.phone").value("0177 233455"))
    }
}
