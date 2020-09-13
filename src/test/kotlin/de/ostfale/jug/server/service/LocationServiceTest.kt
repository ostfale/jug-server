package de.ostfale.jug.server.service

import com.ninjasquad.springmockk.MockkBean
import de.ostfale.jug.server.CreateLocationList
import de.ostfale.jug.server.domain.Location
import de.ostfale.jug.server.repository.LocationRepository
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.verify
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*

@Tag("unitTest")
@DisplayName("Perform CRUD operations for Location service")
@ExtendWith(SpringExtension::class)
internal class LocationServiceTest {

    @MockkBean
    lateinit var locationRepository: LocationRepository

    private lateinit var locationService: LocationService

    @BeforeEach
    fun configureSUT() {
        locationService = LocationService(locationRepository)
    }

    @Test
    @DisplayName("Service calls delete location method of repo")
    fun `Verify that repository delete method is called by service`() {
        // given
        every { locationRepository.deleteById(any()) } just Runs
        // when
        locationService.deleteById(1L)
        // then
        verify { locationRepository.deleteById(1L) }
    }

    @Test
    @DisplayName("Retrieve list with all locations")
    fun `Find all locations in a repository`() {
        // given
        val locations: Iterable<Location> = CreateLocationList.create()
        // when
        every { locationRepository.findAll() } returns locations
        val result = locationService.findAll()
        // then
        Assertions.assertEquals(2, result.size)
        Assertions.assertEquals("Academic Work", result[0].name)
        Assertions.assertEquals("20457", result[0].postalCode)
        Assertions.assertEquals("20099", result[1].postalCode)
    }

    @Test
    @DisplayName("Retrieve a location by its Id")
    fun `Find a location by id`() {
        // given
        val locations: Iterable<Location> = CreateLocationList.create()
        // when
        every { locationRepository.findById(any()) } returns Optional.of(locations.first())
        val result = locationService.getById(1)
        // then
        Assertions.assertEquals("Academic Work", result.name)
        Assertions.assertEquals("DEU", result.country)
        Assertions.assertEquals("Hamburg", result.city)
        Assertions.assertEquals("20457", result.postalCode)
        Assertions.assertEquals("Großer Burstah", result.streetName)
        Assertions.assertEquals("50-52", result.streetNumber)
        Assertions.assertEquals("Meeting 1", result.rooms.first().name)
    }
}