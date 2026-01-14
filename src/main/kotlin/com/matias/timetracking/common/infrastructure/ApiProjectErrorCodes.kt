package com.matias.timetracking.common.infrastructure

enum class ApiProjectErrorCodes(val msg: String) {
    DUPLICATED_PROJECT_TITLE("The project title already exists, try another one"),
    PROJECT_ID_DOES_NOT_EXIST("The informed project ID does not exist"),
}