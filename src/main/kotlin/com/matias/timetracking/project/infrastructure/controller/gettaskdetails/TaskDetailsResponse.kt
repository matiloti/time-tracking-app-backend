package com.matias.timetracking.project.infrastructure.controller.gettaskdetails

import java.util.*

data class TaskDetailsDto(
    val id: UUID? = null,
    val name: String,
    val description: String? = null,
    val priority: PriorityDto,
    val completed: Boolean,
    val milestoneId: UUID,
    val milestoneName: String,
)

data class PriorityDto(val id: Int, val name: String)
