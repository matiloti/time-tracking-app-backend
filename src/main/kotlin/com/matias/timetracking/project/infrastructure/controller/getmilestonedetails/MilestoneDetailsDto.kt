package com.matias.timetracking.project.infrastructure.controller.getmilestonedetails

import java.time.LocalDate
import java.util.*

data class MilestoneDetailsDto(
    val id: UUID? = null,
    val name: String,
    val description: String?,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val projectId: UUID,
    val projectName: String,
    val tasks: List<TaskItem>,
)

data class TaskItem(
    val id: UUID,
    val name: String,
    val description: String? = null,
    val priorityId: Int,
    val completed: Boolean,
)
