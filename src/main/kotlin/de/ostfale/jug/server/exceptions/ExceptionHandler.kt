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
}