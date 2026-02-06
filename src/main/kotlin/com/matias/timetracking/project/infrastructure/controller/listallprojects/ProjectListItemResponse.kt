package com.matias.timetracking.project.infrastructure.controller.listallprojects

import java.util.*

class ProjectListItemResponse(
    val id: UUID,
    val name: String,
    val description: String?,
    val categoryId: Int,
)
