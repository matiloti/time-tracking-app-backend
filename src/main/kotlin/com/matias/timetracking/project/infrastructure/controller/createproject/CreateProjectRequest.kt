package com.matias.timetracking.project.infrastructure.controller.createproject

import jakarta.validation.constraints.NotNull

data class CreateProjectRequest(
    @field:NotNull(message = "Project name is required")
    val name: String,
    val description: String?,
    @field:NotNull(message = "Project name is required")
    val categoryId: Int,
)
