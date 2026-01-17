package com.matias.timetracking.project.infrastructure.controller.createmilestone

import jakarta.validation.constraints.NotNull
import java.time.LocalDate

data class CreateMilestoneRequest(
    @field:NotNull(message = "Milestone name is required")
    val name: String,
    val description: String?,
    val startDate: LocalDate?,
    val endDate: LocalDate?,
)