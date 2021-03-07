package de.ostfale.jug.server.config

import de.ostfale.jug.server.repository.PersonRepository
import org.springframework.boot.actuate.endpoint.annotation.Endpoint
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation
import org.springframework.stereotype.Component

@Component
@Endpoint(id = "db-stats")
class DBStatsEndpoint(
    val personRepository: PersonRepository
) {

    @ReadOperation
    fun personStats(): Stats {
        return Stats(personRepository.count())
    }
}

data class Stats(val count: Long)