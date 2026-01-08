package com.matias.timetracking.project.application.usecase.create

data class ProjectCreationCommand(
    val name: String,
    val description: String,
    val categoryId: Int
)