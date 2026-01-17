package com.matias.timetracking.project.application.usecase.createmilestone

import java.time.LocalDate
import java.util.*

data class CreateMilestoneCommand(
    val projectId: UUID,
    val name: String,
    val description: String?,
    val startDate: LocalDate?,
    val endDate: LocalDate?
)