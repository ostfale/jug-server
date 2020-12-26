package de.ostfale.jug.server.repository

import de.ostfale.jug.server.domain.Tag
import org.springframework.data.repository.CrudRepository

interface TagRepository : CrudRepository<Tag, Long>