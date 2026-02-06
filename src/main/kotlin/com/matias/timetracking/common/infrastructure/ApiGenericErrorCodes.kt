package com.matias.timetracking.common.infrastructure

enum class ApiGenericErrorCodes(val defaultMsg: String) {
    SERVER_ERROR("An unexpected error occurred, please wait and try again"),
    RESOURCE_NOT_FOUND("The requested resource does not exist"),
    DOMAIN_ERROR("An unexpected error occurred, please wait and try again"),
    PAYLOAD_VALIDATION_ERROR("Validation failed for request"),
    ;

    fun getApiError() = ApiError(
        code = this.name,
        message = this.defaultMsg,
    )

    fun getApiError(msg: String) = ApiError(
        code = this.name,
        message = msg,
    )

    fun getApiError(msg: String, details: Map<String, Any>) = ApiError(
        code = this.name,
        message = msg,
        details = details,
    )

    fun getApiError(details: Map<String, Any>) = ApiError(
        code = this.name,
        message = this.defaultMsg,
        details = details,
    )
}
