package com.matias.timetracking.common.infrastructure

data class ApiError(
    val code: String,
    val message: String,
    val details: Map<String, Any>? = null,
)
