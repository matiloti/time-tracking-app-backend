package com.matias.timetracking.common.application

class DomainException(
    val title: String,
    val detail: String? = null
): RuntimeException(title)