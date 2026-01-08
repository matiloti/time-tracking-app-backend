package com.matias.timetracking.project.application.usecase.create

import com.matias.timetracking.project.domain.repository.ProjectRepositoryPort
import com.matias.timetracking.project.domain.aggregate.Project
import java.time.LocalDateTime
import java.util.UUID

class CreateProjectUseCase(val projectRepository: ProjectRepositoryPort) {
    fun execute(request: CreateProjectCommand): CreateProjectResponse {
        val project = request.mapToDomain()
        projectRepository.save(project)
        return CreateProjectResponse(project.id)
    }

    fun CreateProjectCommand.mapToDomain() = Project(
        UUID.randomUUID(),
        name,
        description,
        categoryId,
        LocalDateTime.now()
    )
}