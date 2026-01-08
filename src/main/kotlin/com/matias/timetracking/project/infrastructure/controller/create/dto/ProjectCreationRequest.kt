package com.matias.timetracking.project.infrastructure.controller.create.dto

data class ProjectCreationRequest(
    val name: String,
    val description: String,
    val categoryId: Int
)