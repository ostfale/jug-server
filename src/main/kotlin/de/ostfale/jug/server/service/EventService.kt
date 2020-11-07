package de.ostfale.jug.server.service

import de.ostfale.jug.server.domain.Event
import de.ostfale.jug.server.exceptions.EventNotFoundException
import de.ostfale.jug.server.repository.EventRepository
import org.springframework.stereotype.Service

@Service
class EventService(
    private val eventRepository: EventRepository
) {

    fun findAll(): List<Event> = eventRepository.findAll().toList()

    fun getById(id: Long): Event = eventRepository.findById(id).orElseThrow { EventNotFoundException(id) }

    fun deleteById(id: Long) = eventRepository.deleteById(id)

    fun save(event: Event): Event = eventRepository.save(event)
}