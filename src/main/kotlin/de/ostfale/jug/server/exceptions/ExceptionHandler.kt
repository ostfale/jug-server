package de.ostfale.jug.server.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class ExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(PersonNotFoundException::class)
    fun handlePersonNotFoundException(
        personNotFoundException: PersonNotFoundException,
        request: WebRequest
    ): ResponseEntity<Any> {
        val body = mutableMapOf("message" to personNotFoundException.message)
        return ResponseEntity(body, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(LocationNotFoundException::class)
    fun handleLocationNotFoundException(
        locationNotFoundException: LocationNotFoundException,
        request: WebRequest
    ): ResponseEntity<Any> {
        val body = mutableMapOf("message" to locationNotFoundException.message)
        return ResponseEntity(body, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(EventNotFoundException::class)
    fun handleEventNotFoundException(
        eventNotFoundException: EventNotFoundException,
        request: WebRequest
    ): ResponseEntity<Any> {
        val body = mutableMapOf("message" to eventNotFoundException.message)
        return ResponseEntity(body, HttpStatus.NOT_FOUND)
    }
}