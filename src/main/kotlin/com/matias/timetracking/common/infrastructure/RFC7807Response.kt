package com.matias.timetracking.common.infrastructure

import org.springframework.http.HttpStatus
import java.net.URI

open class RFC7807Response(
    val type: URI,
    val title: String,
    val status: HttpStatus,
    val detail: String?,
    val instance: URI?
)