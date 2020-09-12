package de.ostfale.jug.server.exceptions


class PersonNotFoundException(id: Long) : RuntimeException("Person with ID: $id could not be found!")

class LocationNotFoundException(id: Long) : RuntimeException("Location with ID: $id could not be found!")