package de.ostfale.jug.server

import Person

object CreatePersonModel {
    fun create(
            anId: Long = 1,
            aFirstName: String = "Max",
            aLastName: String = "Schneider",
            anEmail: String = "mschneider@mail.de",
            aPhone: String = "0177 233455",
            aBio: String = "This is a bio....",
    ): Person {
        return Person(
                id = anId,
                firstName = aFirstName,
                lastName = aLastName,
                email = anEmail,
                phone = aPhone,
                bio = aBio)
    }
}

object CreatePersonList {
    fun create(): List<Person> {
        val anna = CreatePersonModel.create()
        val moritz = CreatePersonModel.create(anId = 2, aFirstName = "Moritz", aLastName = "Weber", aPhone = "0177 554433", anEmail = "mweber@mail.de")
        return listOf(anna, moritz)
    }
}