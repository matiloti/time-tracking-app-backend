package com.matias.timetracking.project.application.usecase.createmilestone

import java.time.LocalDateTime
import java.util.UUID

data class CreateMilestoneCommand(
    val projectId: UUID,
    val name: String,
    val description: String?,
    val startDate: LocalDateTime?,
    val endDate: LocalDateTime?
)