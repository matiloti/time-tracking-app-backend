package com.matias.timetracking.common.infrastructure

import jakarta.servlet.http.HttpServletRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.dao.DataAccessResourceFailureException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.jdbc.BadSqlGrammarException
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
            .body(ApiError(
                ApiGenericErrorCodes.SERVER_ERROR.name,
                ApiGenericErrorCodes.SERVER_ERROR.msg
            ))
    }

    @ExceptionHandler(BadSqlGrammarException::class)
    fun handleBadSqlGrammarException(e: BadSqlGrammarException, request: HttpServletRequest): ResponseEntity<ApiError> {
        logError(request, e)
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                ApiError(
                    ApiGenericErrorCodes.SERVER_ERROR.name,
                    ApiGenericErrorCodes.SERVER_ERROR.msg
                )
            )
    }

    @ExceptionHandler(DataAccessResourceFailureException::class)
    fun handleDataAccessResourceFailureException(e: DataAccessResourceFailureException, request: HttpServletRequest): ResponseEntity<ApiError> {
        logError(request, e)
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                ApiError(
                    ApiGenericErrorCodes.SERVER_ERROR.name,
                    ApiGenericErrorCodes.SERVER_ERROR.msg
                )
            )
    }

    @ExceptionHandler(NoResourceFoundException::class)
    fun handleNoResourceFoundExceptionException(e: NoResourceFoundException, request: HttpServletRequest): ResponseEntity<ApiError> {
        logError(request, e)
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(
                ApiError(
                    ApiGenericErrorCodes.RESOURCE_NOT_FOUND.name,
                    ApiGenericErrorCodes.RESOURCE_NOT_FOUND.msg
                )
            )
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