package de.ostfale.jug.server.domain

import Person
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
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
    var locationStatus: LocationStatus,
    var scheduleStatus: ScheduleStatus,
    var locationId: Long?,
    var speakerIds: MutableSet<PersonRef> = HashSet(),
    var history: Set<Note> = HashSet()
) {
    fun addSpeaker(aPerson: Person) {
        val personId = aPerson.id
        if (personId != null) this.speakerIds.add(PersonRef(person = personId))
    }
}

data class EventDTO(
    val id: Long?,
    var title: String,
    var content: String?,
    var remark: String?,
    var dateTime: LocalDateTime?,
    var isOnlineEvent: Boolean = true,
    var isComplete: Boolean = false,
    var eventStatus: EventStatus,
    var locationStatus: LocationStatus,
    var scheduleStatus: ScheduleStatus,
    var location: LocationDTO?,
    var speaker: Set<Person> = HashSet(),
    var history: Set<Note> = HashSet()
)

@Table("speaker_event")
data class PersonRef(
    var person: Long
)

data class Note(
    var timestamp: LocalDateTime,
    var content: String
)

enum class EventStatus {
    PLANNED, ONLINE, DONE, POSTPONED, CANCELED
}

enum class LocationStatus {
    PLANNED, CONFIRMED, DENIED
}

enum class ScheduleStatus {
    PLANNED, CONFIRMED, POSTPONED
}
