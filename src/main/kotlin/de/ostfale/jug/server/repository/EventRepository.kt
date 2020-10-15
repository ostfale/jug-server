package de.ostfale.jug.server.repository

import de.ostfale.jug.server.domain.Event
import org.springframework.data.repository.CrudRepository

interface EventRepository : CrudRepository<Event, Long>