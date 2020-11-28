package de.ostfale.jug.server.controller

import com.ninjasquad.springmockk.MockkBean
import de.ostfale.jug.server.*
import de.ostfale.jug.server.service.EventService
import de.ostfale.jug.server.service.LocationService
import de.ostfale.jug.server.service.PersonService
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@Tag("unitTest")
@DisplayName("Test REST access for event controller")
@ActiveProfiles("test")
@WebMvcTest(controllers = [MockMvcValidationConfiguration::class, EventController::class])
internal class EventControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockkBean
    lateinit var personService: PersonService

    @MockkBean
    lateinit var locationService: LocationService

    @MockkBean
    lateinit var eventService: EventService

    @Test
    @DisplayName("Delete an event")
    internal fun `Delete a person and receive 'No Content' status`() {
        // when
        every { eventService.deleteById(any()) } just Runs
        // then
        mockMvc.delete("/api/v1/event/1")
            .andExpect { MockMvcResultMatchers.status().isNoContent }
    }

    @Test
    @DisplayName("Read all events per REST from DB")
    internal fun `Get all events from database`() {
        // given
        val events = CreateEventList.create()
        // when
        every { personService.getById(any()) } returns CreatePersonModel.create()
        every { locationService.getById(any()) } returns CreateLocationModel.create()
        every { eventService.findAll() } returns events
        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/event/").accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.[0].title").value("Kotlin"))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.[1].title").value("Java"))


    }
}