package com.matias.timetracking.project.infrastructure.controller.createproject

data class CreateProjectRequest(
    val name: String,
    val description: String,
    val categoryId: Int
)