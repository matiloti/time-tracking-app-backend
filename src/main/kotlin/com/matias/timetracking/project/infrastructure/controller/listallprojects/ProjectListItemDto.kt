package com.matias.timetracking.project.infrastructure.controller.listallprojects

import java.util.UUID

class ProjectListItemDto(
    val id: UUID,
    val name: String,
    val description: String,
    val categoryId: Int
)