package com.matias.timetracking.project.application.usecase.findproject

import com.matias.timetracking.project.domain.aggregate.Project
import com.matias.timetracking.project.domain.repository.ProjectRepository

class FindAllProjectsUseCase(val projectRepository: ProjectRepository) {
    fun findAll(): List<Project> {
        return projectRepository.findAll();
    }
}