package com.matias.timetracking.project.application.usecase.createtask

import java.util.*

data class CreateTaskCommand(
    val name: String,
    val description: String?,
    val priority: Int,
    val milestoneId: UUID,
)
