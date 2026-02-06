package com.matias.timetracking.project.infrastructure.dao.row

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime
import java.util.*

@Table("tasks")
data class TaskRow(
    @Id
    var id: UUID? = null,
    val milestoneId: UUID,
    val name: String,
    val description: String?,
    val priorityId: Int,
    val completed: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)
