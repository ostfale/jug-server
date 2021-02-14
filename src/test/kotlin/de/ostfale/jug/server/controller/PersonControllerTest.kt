package de.ostfale.jug.server.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.ninjasquad.springmockk.MockkBean
import de.ostfale.jug.server.CreatePersonList
import de.ostfale.jug.server.CreatePersonModel
import de.ostfale.jug.server.service.PersonService
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete
import org.springframework.restdocs.operation.preprocess.Preprocessors.*
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@Tag("unitTest")
@DisplayName("Test REST access for person controller")
@ActiveProfiles("test")
@WebMvcTest(controllers = [MockMvcValidationConfiguration::class, PersonController::class])
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
internal class PersonControllerTest(
    @Autowired val mockMvc: MockMvc
) {

    @MockkBean
    lateinit var personService: PersonService

    @Test
    @DisplayName("Delete a person")
    internal fun `Delete a person and receive 'No Content' status`() {
        // when
        every { personService.deleteById(any()) } just Runs
        // then
        mockMvc.perform(delete("/api/v1/person/1"))
            .andExpect(status().isNoContent)
            .andDo(document("deletePerson"))
            .andReturn()
    }

    @Test
    @DisplayName("Read all persons per REST from DB")
    internal fun `getAllPersons`() {
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
            .andDo(document("getAllPersons", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
    }

    @Test
    @DisplayName("Read a person with an existing ID from DB")
    internal fun `Read an existing person from database`() {
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

    @Test
    @DisplayName("Create new person")
    internal fun `Test create user valid scenario`() {
        // given
        val max = CreatePersonModel.create()
        // when
        every { personService.save(max) } returns max
        // then
        mockMvc.post("/api/v1/person/") {
            contentType = MediaType.APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(max)
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { this.isCreated() }
            content { contentType(MediaType.APPLICATION_JSON) }
            content { json("""{"id":10}""") }
        }
    }
}
