package com.matias.timetracking.project.domain.aggregate

import java.time.LocalDateTime
import java.util.UUID

class Milestone(
    val id: UUID,
    val projectId: UUID,
    val name: String,
    val description: String?,
    val startDate: LocalDateTime?,
    val endDate: LocalDateTime?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)