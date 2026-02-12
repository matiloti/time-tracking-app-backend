package com.matias.timetracking.project.infrastructure.controller.gettaskdetails

import java.util.*

data class TaskDetailsResponse(
    val id: UUID,
    val name: String,
    val description: String? = null,
    val priorityId: Int,
    val completed: Boolean,
    val milestoneId: UUID,
    val milestoneName: String,
)
