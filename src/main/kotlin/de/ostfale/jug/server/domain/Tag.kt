package de.ostfale.jug.server.domain

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("tag_name")
data class Tag(
    @Column("tag_id") @Id var id: Long? = null,
    val name: String,
    var eventIds: MutableSet<EventRef> = HashSet()
) {

    fun addEvent(anEvent: Event) {
        this.eventIds.add(EventRef(event = anEvent.id))
    }
}

@Table("event_tag")
data class EventRef(
    var event: Long?
)

