package com.matias.timetracking.common.infrastructure

import com.matias.timetracking.project.domain.exception.DomainException
import com.matias.timetracking.project.domain.exception.DuplicatedProjectNameException
import com.matias.timetracking.project.domain.exception.ProjectIdNotFoundException
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.resource.NoResourceFoundException

@ControllerAdvice
class GlobalControllerAdvice {

    @ExceptionHandler(Exception::class)
    fun handleGenericException(e: Exception, request: HttpServletRequest): ResponseEntity<ApiError> {
        logError(request, e)
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ApiGenericErrorCodes.SERVER_ERROR.getApiError())
    }

    @ExceptionHandler(NoResourceFoundException::class)
    fun handleNoResourceFoundException(
        e: NoResourceFoundException,
        request: HttpServletRequest,
    ): ResponseEntity<ApiError> {
        logError(request, e)
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ApiGenericErrorCodes.RESOURCE_NOT_FOUND.getApiError())
    }

    @ExceptionHandler(DomainException::class)
    fun handleDomainException(e: DomainException, request: HttpServletRequest): ResponseEntity<ApiError> {
        logError(request, e)
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ApiGenericErrorCodes.DOMAIN_ERROR.getApiError(e.message!!))
    }

    @ExceptionHandler(ProjectIdNotFoundException::class)
    fun handleProjectIdNotFoundException(
        e: ProjectIdNotFoundException,
        request: HttpServletRequest,
    ): ResponseEntity<ApiError> {
        logError(request, e)
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ApiProjectErrorCodes.PROJECT_ID_NOT_FOUND.getApiError(e.message!!))
    }

    @ExceptionHandler(DuplicatedProjectNameException::class)
    fun handleDuplicatedProjectNameException(
        e: DuplicatedProjectNameException,
        request: HttpServletRequest,
    ): ResponseEntity<ApiError> {
        logError(request, e)
        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(ApiProjectErrorCodes.DUPLICATED_PROJECT_NAME.getApiError(e.message!!))
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException(
        e: HttpMessageNotReadableException,
        request: HttpServletRequest,
    ): ResponseEntity<ApiError> {
        logError(request, e)
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ApiProjectErrorCodes.INVALID_REQUEST_BODY.getApiError())
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(
        e: MethodArgumentNotValidException,
        request: HttpServletRequest,
    ): ResponseEntity<ApiError> {
        logError(request, e)
        val errors = e.bindingResult.fieldErrors.associate {
            it.field to (it.defaultMessage ?: "Invalid value")
        }
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ApiGenericErrorCodes.PAYLOAD_VALIDATION_ERROR.getApiError(errors))
    }

    companion object {
        val logger: Logger = LoggerFactory.getLogger(GlobalControllerAdvice::class.java)
        private fun logError(request: HttpServletRequest, e: Exception) = logger.error(
            """[${e.javaClass.simpleName}] ${request.method} ${request.requestURI}
                |>>> Message: ${e.message}
                |>>> Cause: ${e.cause}
                |>>> Trace: ${e.printStackTrace()}
            """.trimMargin(),
        )
    }
}
