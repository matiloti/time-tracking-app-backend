package com.matias.timetracking.project.application.usecase.find

import com.matias.timetracking.project.domain.aggregate.Project
import com.matias.timetracking.project.domain.repository.ProjectRepositoryPort

class FindAllProjectsUseCase(val projectRepository: ProjectRepositoryPort) {
    fun findAll(): List<Project> {
        return projectRepository.findAll();
    }
}