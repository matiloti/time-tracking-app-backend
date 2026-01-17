package com.matias.timetracking.common.infrastructure

enum class ApiGenericErrorCodes(val msg: String) {
    SERVER_ERROR("An unexpected error occurred, please wait and try again"),
    RESOURCE_NOT_FOUND("The requested resource does not exist"),
}