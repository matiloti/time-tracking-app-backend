package com.matias.timetracking.project.application.usecase.createproject

import com.matias.timetracking.project.domain.aggregate.Project
import com.matias.timetracking.project.domain.repository.ProjectRepository
import org.springframework.stereotype.Service

@Service
class CreateProjectUseCase(val projectRepository: ProjectRepository) {
    fun execute(request: CreateProjectCommand): CreateProjectResponse =
        request
            .createProjectFromRequest()
            .save()
            .mapToResponse()

    fun CreateProjectCommand.createProjectFromRequest() =
        Project.createNewProject(
            name,
            description,
            categoryId
        )

    fun Project.save() = apply { projectRepository.save(this) }

    fun Project.mapToResponse(): CreateProjectResponse =
        CreateProjectResponse(id)
}