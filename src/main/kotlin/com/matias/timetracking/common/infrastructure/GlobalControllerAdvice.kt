package com.matias.timetracking.common.infrastructure

import com.matias.timetracking.common.application.ApplicationException
import com.matias.timetracking.common.application.DomainException
import com.matias.timetracking.common.application.EntityAlreadyExistsException
import com.matias.timetracking.common.application.InfrastructureException
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.net.URI

@ControllerAdvice
class GlobalControllerAdvice {
    @ExceptionHandler(InfrastructureException::class)
    fun handleDomainException(
        exception: InfrastructureException,
        request: HttpServletRequest
    ): ResponseEntity<RFC7807Response> =
        ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(RFC7807Response(
                URI.create(""),
                exception.title,
                HttpStatus.INTERNAL_SERVER_ERROR,
                exception.detail,
                URI.create(trimDomain(request.requestURI))
            ))

    @ExceptionHandler(DomainException::class)
    fun handleDomainException(
        exception: DomainException,
        request: HttpServletRequest
    ): ResponseEntity<RFC7807Response> =
        ResponseEntity
            .status(HttpStatus.UNPROCESSABLE_ENTITY)
            .body(RFC7807Response(
                URI.create(""),
                exception.title,
                HttpStatus.UNPROCESSABLE_ENTITY,
                exception.detail,
                URI.create(trimDomain(request.requestURI))
            ))

    @ExceptionHandler(EntityAlreadyExistsException::class)
    fun handleResourceAlreadyExistsException(
        exception: EntityAlreadyExistsException,
        request: HttpServletRequest
    ): ResponseEntity<RFC7807Response> =
        ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(RFC7807Response(
                URI.create(""),
                exception.title,
                HttpStatus.CONFLICT,
                exception.detail,
                URI.create(trimDomain(request.requestURI))
            ))

    @ExceptionHandler(ApplicationException::class)
    fun handleAppException(
        exception: ApplicationException,
        request: HttpServletRequest
    ): ResponseEntity<RFC7807Response> =
        ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(RFC7807Response(
                URI.create(""),
                exception.title,
                HttpStatus.BAD_REQUEST,
                exception.detail,
                URI.create(trimDomain(request.requestURI))
            ))

    @ExceptionHandler(Exception::class)
    fun handleAppException(): ResponseEntity<RFC7807Response> =
        ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(RFC7807Response(
                URI.create(""),
                "An unexpected error ocurred",
                HttpStatus.BAD_REQUEST,
                null,
                null
            ))
    companion object {
        private val URI_REGEX = Regex.fromLiteral("(([a-z]+):\\/\\/)?([a-z-\\.]+)(\\.([a-z]+))?(:(\\d{1,4}))?")
        private fun trimDomain(uri: String) = uri.replace(URI_REGEX, "")
    }
}