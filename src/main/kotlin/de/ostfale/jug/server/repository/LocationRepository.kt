package de.ostfale.jug.server.repository

import de.ostfale.jug.server.domain.Location
import org.springframework.data.repository.CrudRepository

interface LocationRepository : CrudRepository<Location, Long> {
}