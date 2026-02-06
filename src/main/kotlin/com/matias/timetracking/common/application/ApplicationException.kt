package com.matias.timetracking.common.application

class ApplicationException(val title: String, val detail: String? = null) : RuntimeException(title)
