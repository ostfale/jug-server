package de.ostfale.jug.server.converter

import com.ninjasquad.springmockk.MockkBean
import de.ostfale.jug.server.CreateEventModel
import de.ostfale.jug.server.CreateLocationModel
import de.ostfale.jug.server.CreatePersonModel
import de.ostfale.jug.server.service.LocationService
import de.ostfale.jug.server.service.PersonService
import io.mockk.every
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.junit.jupiter.SpringExtension

@Tag("unitTest")
@DisplayName("Converter test for Event <-> EventDTO")
@ExtendWith(SpringExtension::class)
internal class EventConverterTest {

    @MockkBean
    lateinit var personService: PersonService

    @MockkBean
    lateinit var locationService: LocationService

    @Test
    @DisplayName("Conversion Event -> DTO")
    fun `Verify conversion from Event entity to dto`() {
        // given
        val max = CreatePersonModel.create()
        val academicWork = CreateLocationModel.create()
        every { personService.getById(any()) } returns max
        every { locationService.getById(any()) } returns academicWork
        val kotlinEvent = CreateEventModel.create()
        // when
        val eventDto = kotlinEvent.toDTO(personService, locationService)
        // then
        assertEquals(eventDto.id, kotlinEvent.id)
        assertEquals(eventDto.title, kotlinEvent.title)
        assertEquals(eventDto.content, kotlinEvent.content)
        assertEquals(eventDto.dateTime, kotlinEvent.dateTime)
        assertEquals(eventDto.location, academicWork)
        assertEquals(eventDto.speaker, max)
        assertEquals(1, eventDto.history.size)
    }
}