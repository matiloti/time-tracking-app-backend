package com.matias.timetracking.common.infrastructure

import com.matias.timetracking.project.domain.exception.DuplicatedProjectNameException
import com.matias.timetracking.project.domain.exception.ProjectIdNotFoundException
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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
    fun handleNoResourceFoundExceptionException(e: NoResourceFoundException, request: HttpServletRequest): ResponseEntity<ApiError> {
        logError(request, e)
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ApiGenericErrorCodes.RESOURCE_NOT_FOUND.getApiError())
    }

    @ExceptionHandler(ProjectIdNotFoundException::class)
    fun handleNoResourceFoundExceptionException(e: ProjectIdNotFoundException, request: HttpServletRequest): ResponseEntity<ApiError> {
        logError(request, e)
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ApiProjectErrorCodes.PROJECT_ID_NOT_FOUND.getApiError(e.message!!))
    }

    @ExceptionHandler(DuplicatedProjectNameException::class)
    fun handleNoResourceFoundExceptionException(e: DuplicatedProjectNameException, request: HttpServletRequest): ResponseEntity<ApiError> {
        logError(request, e)
        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(ApiProjectErrorCodes.DUPLICATED_PROJECT_NAME.getApiError(e.message!!))
    }

    companion object {
        val logger: Logger = LoggerFactory.getLogger(GlobalControllerAdvice::class.java)
        private fun logError(
            request: HttpServletRequest,
            e: Exception
        ) = logger.error(
            """[${e.javaClass.simpleName}] ${request.method} ${request.requestURI}
                |\n\n\t>>> Message: ${e.message} 
                |\n\n\t>>> Cause: ${e.cause}""".trimMargin()
        )    }
}