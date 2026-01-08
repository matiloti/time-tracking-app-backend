package com.matias.timetracking.project.application.usecase.create

import com.matias.timetracking.project.domain.repository.ProjectRepositoryPort
import com.matias.timetracking.project.domain.aggregate.Project
import java.time.LocalDateTime
import java.util.UUID

class ProjectCreationUseCase(val projectRepository: ProjectRepositoryPort) {
    fun execute(request: ProjectCreationCommand): ProjectCreationResponse {
        val project = request.mapToDomain()
        projectRepository.save(project)
        return ProjectCreationResponse(project.id)
    }

    fun ProjectCreationCommand.mapToDomain() = Project(
        UUID.randomUUID(),
        name,
        description,
        categoryId,
        LocalDateTime.now()
    )
}