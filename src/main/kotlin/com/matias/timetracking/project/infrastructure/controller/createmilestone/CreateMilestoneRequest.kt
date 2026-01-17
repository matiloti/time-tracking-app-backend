package com.matias.timetracking.project.infrastructure.controller.createmilestone

import java.time.LocalDate

data class CreateMilestoneRequest(
    val name: String,
    val description: String?,
    val startDate: LocalDate?,
    val endDate: LocalDate?,
)