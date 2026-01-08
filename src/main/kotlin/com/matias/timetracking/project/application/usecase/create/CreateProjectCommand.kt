package com.matias.timetracking.project.application.usecase.create

data class CreateProjectCommand(
    val name: String,
    val description: String,
    val categoryId: Int
)