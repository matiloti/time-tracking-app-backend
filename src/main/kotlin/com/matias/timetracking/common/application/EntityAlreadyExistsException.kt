package com.matias.timetracking.common.application

class EntityAlreadyExistsException(
    val title: String,
    val detail: String? = null
): RuntimeException(title)