package com.matias.timetracking.common.infrastructure

enum class ApiProjectErrorCodes(val defaultMsg: String) {
    PROJECT_ID_NOT_FOUND("Project with ID not found"),
    DUPLICATED_PROJECT_NAME("Duplicated project name"),
    INVALID_REQUEST_BODY("Invalid request body. Please, check for types and required fields.");

    fun getApiError() = ApiError(
        code = this.name,
        message = this.defaultMsg
    )

    fun getApiError(msg: String) = ApiError(
        code = this.name,
        message = msg
    )
}