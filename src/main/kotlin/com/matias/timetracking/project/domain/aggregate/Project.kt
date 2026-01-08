package com.matias.timetracking.project.domain.aggregate

import java.time.LocalDateTime
import java.util.UUID

class Project(
    val id: UUID,
    val title: String,
    val description: String,
    val categoryId: Int,
    val createdAt: LocalDateTime?
)