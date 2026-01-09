package com.matias.timetracking.project.infrastructure.controller.createmilestone.dto

import java.time.LocalDateTime

data class CreateMilestoneRequest(
    val name: String,
    val description: String?,
    val startDate: LocalDateTime?,
    val endDate: LocalDateTime?,
)