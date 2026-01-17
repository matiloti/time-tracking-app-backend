package com.matias.timetracking.project.application.usecase.createproject

data class CreateProjectCommand(
    val name: String,
    val description: String?,
    val categoryId: Int
)