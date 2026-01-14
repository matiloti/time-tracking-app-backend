package com.matias.timetracking.project.infrastructure.dao.row

import java.time.LocalDateTime
import java.util.UUID

data class ProjectRow(
    val id: UUID,
    val name: String,
    val description: String,
    val categoryId: Int,
    val createdAt: LocalDateTime?
)