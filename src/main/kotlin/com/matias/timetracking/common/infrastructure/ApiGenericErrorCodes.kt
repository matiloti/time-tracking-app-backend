package com.matias.timetracking.common.infrastructure

enum class ApiGenericErrorCodes(val defaultMsg: String) {
    SERVER_ERROR("An unexpected error occurred, please wait and try again"),
    RESOURCE_NOT_FOUND("The requested resource does not exist"),
    DOMAIN_ERROR("An unexpected error occurred, please wait and try again");

    fun getApiError() = ApiError(
        code = this.name,
        message = this.defaultMsg
    )

    fun getApiError(msg: String) = ApiError(
        code = this.name,
        message = msg
    )
}