package com.matias.timetracking.project.domain.aggregate

import java.time.LocalDateTime
import java.util.*

class Milestone(
    val id: UUID,
    val projectId: UUID,
    val name: String,
    val description: String?,
    val startDate: LocalDateTime?,
    val endDate: LocalDateTime?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {
    fun copy(): Milestone = Milestone(
        id = id,
        projectId = projectId,
        name = name,
        description = description,
        startDate = startDate,
        endDate = endDate,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}