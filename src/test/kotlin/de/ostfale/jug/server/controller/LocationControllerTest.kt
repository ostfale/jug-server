package de.ostfale.jug.server.controller

import com.ninjasquad.springmockk.MockkBean
import de.ostfale.jug.server.CreateLocationList
import de.ostfale.jug.server.CreateLocationModel
import de.ostfale.jug.server.CreatePersonModel
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
@DisplayName("Test REST access for location controller")
@ActiveProfiles("test")
@WebMvcTest (controllers = [LocationController::class])
internal class LocationControllerTest(@Autowired val mockMvc: MockMvc) {

    @MockkBean
    lateinit var personService: PersonService

    @MockkBean
    lateinit var locationService: LocationService

    @Test
    @DisplayName("Read all locations per REST from DB")
    internal fun `Get all locations`() {
        // given
        val locations = CreateLocationList.create()
        // when
        every { personService.getById(1L) } returns CreatePersonModel.create()
        every { personService.getById(2L) } returns CreatePersonModel.create(anId = 2L, aFirstName = "Iwan")
        every { locationService.findAll() } returns locations
        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/location/").accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.[0].name").value("Academic Work"))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.[1].name").value("HAW"))
    }

    @Test
    @DisplayName("Read a location with an existing ID from DB")
    internal fun `Read an existing location`() {
        // given
        val dbLocation = CreateLocationModel.create()
        // when
        val max = CreatePersonModel.create()
        every { personService.getById(any()) } returns CreatePersonModel.create()
        every { locationService.getById(any()) } returns dbLocation
        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/location/1").accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.name").value("Academic Work"))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.postalCode").value("20457"))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.contact.firstName").value("Max"))
    }

    @Test
    @DisplayName ("Delete a location")
    internal fun `Delete a location and receive 'No Content' status`() {
        // when
        every { locationService.deleteById(any()) } just Runs
        // then
        mockMvc.delete("/api/v1/location/1")
            .andExpect { MockMvcResultMatchers.status().isNoContent }
    }
}