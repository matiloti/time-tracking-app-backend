package com.matias.timetracking.common.infrastructure

enum class ApiProjectErrorCodes(val defaultMsg: String) {
    PROJECT_ID_NOT_FOUND("Project not found"),
    MILESTONE_ID_NOT_FOUND("Milestone not found"),
    TASK_ID_NOT_FOUND("Task not found"),
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