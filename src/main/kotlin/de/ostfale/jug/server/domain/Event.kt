package de.ostfale.jug.server.domain

import Person
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import java.time.LocalDateTime

data class Event(
    @Column("event_id") @Id var id: Long? = null,
    var title: String,
    var content: String?,
    var remark: String?,
    var dateTime: LocalDateTime?,
    var isOnlineEvent: Boolean = true,
    var isComplete: Boolean = false,
    var eventStatus: EventStatus,
    var locationId: Long?,
    var speakerId: Long?,
    var history: Set<Note> = HashSet()
)

data class EventDTO(
    val id: Long?,
    var title: String,
    var content: String?,
    var remark: String?,
    var dateTime: LocalDateTime?,
    var isOnlineEvent: Boolean = true,
    var isComplete: Boolean = false,
    var eventStatus: EventStatus,
    var location: Location?,
    var speaker: Person?,
    var history: Set<Note> = HashSet()
)

data class Note(
    var timestamp: LocalDateTime,
    var content: String
)

enum class EventStatus {
    PLANNED, ONLINE, DONE, POSTPONED, CANCELED
}