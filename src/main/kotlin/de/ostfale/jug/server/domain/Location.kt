package de.ostfale.jug.server.domain

import Person
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column

data class Location(@Column("location_id") @Id var id: Long? = null,
                    var name: String,
                    var country: String = "DEU",
                    var city: String = "Hamburg",
                    var postalCode: String,
                    var streetName: String,
                    var streetNumber: String,
                    var contactId: Long,
                    var rooms: Set<Room> = HashSet()
)

data class LocationDTO(val id: Long?,
                       var name: String,
                       var country: String,
                       var city: String,
                       var postalCode: String,
                       var streetName: String,
                       var streetNumber: String,
                       var contact: Person,
                       var rooms: Set<Room>)

data class Room(var name: String,
                var capacity: Int,
                var remark: String? = "")
