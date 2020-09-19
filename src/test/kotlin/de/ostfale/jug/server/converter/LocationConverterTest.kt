package de.ostfale.jug.server.converter

import com.ninjasquad.springmockk.MockkBean
import de.ostfale.jug.server.CreateLocationDto
import de.ostfale.jug.server.CreateLocationModel
import de.ostfale.jug.server.CreatePersonModel
import de.ostfale.jug.server.service.PersonService
import io.mockk.every
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.junit.jupiter.SpringExtension

@Tag("unitTest")
@DisplayName("Perform converter test for Location object")
@ExtendWith(SpringExtension::class)
internal class LocationConverterTest {

    @MockkBean
    lateinit var personService: PersonService

    @Test
    @DisplayName("Conversion Location -> DTO")
    fun `Verify conversion from Location entity to dto`() {
        // given
        val max = CreatePersonModel.create()
        val academicWork = CreateLocationModel.create()
        every { personService.getById(any()) } returns max
        // when
        val locationDto = academicWork.toDTO(personService)
        // then
        assertEquals(locationDto.id, academicWork.id)
        assertEquals(locationDto.name, academicWork.name)
        assertEquals(locationDto.country, academicWork.country)
        assertEquals(locationDto.city, academicWork.city)
        assertEquals(locationDto.postalCode, academicWork.postalCode)
        assertEquals(locationDto.streetName, academicWork.streetName)
        assertEquals(locationDto.streetNumber, academicWork.streetNumber)
        assertEquals(locationDto.contact, max)

    }


    @Test
    @DisplayName("Conversion DTO -> Location")
    fun `Verify conversion from DTO to Location`() {
        // given
        val academicWorkDto = CreateLocationDto.create()
        // when
        val location = academicWorkDto.toLocation()
        // then
        assertEquals(location.id, academicWorkDto.id)
        assertEquals(location.name, academicWorkDto.name)
        assertEquals(location.country, academicWorkDto.country)
        assertEquals(location.city, academicWorkDto.city)
        assertEquals(location.postalCode, academicWorkDto.postalCode)
        assertEquals(location.streetName, academicWorkDto.streetName)
        assertEquals(location.streetNumber, academicWorkDto.streetNumber)
        assertEquals(location.contactId, CreatePersonModel.create().id)
        assertEquals(
            location.rooms.size, 1
        )
    }
}

