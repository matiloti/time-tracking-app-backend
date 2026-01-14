package com.matias.timetracking.project.infrastructure.dao.row

import java.time.LocalDateTime
import java.util.UUID

data class MilestoneRow(
    val id: UUID,
    val projectId: UUID,
    val name: String,
    val description: String?,
    val startDate: LocalDateTime?,
    val endDate: LocalDateTime?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)