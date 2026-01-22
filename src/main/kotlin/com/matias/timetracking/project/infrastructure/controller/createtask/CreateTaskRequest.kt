package com.matias.timetracking.project.infrastructure.controller.createtask

import jakarta.validation.constraints.NotNull

data class CreateTaskRequest(
    @field:NotNull(message = "Task name is required")
    val name: String,
    val description: String?,
    val priority: Int
)