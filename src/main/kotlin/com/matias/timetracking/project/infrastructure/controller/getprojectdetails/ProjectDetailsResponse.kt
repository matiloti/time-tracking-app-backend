package com.matias.timetracking.project.infrastructure.controller.getprojectdetails

import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

data class ProjectDetailsDto(
    val id: UUID,
    val name: String,
    val description: String?,
    val categoryId: Int,
    val createdAt: LocalDateTime?,
    val milestones: List<MilestoneItem>,
)

data class MilestoneItem(
    val id: UUID,
    val name: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
)
