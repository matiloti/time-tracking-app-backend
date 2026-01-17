package com.matias.timetracking.project.application.usecase.createproject

import com.matias.timetracking.project.domain.aggregate.Project
import com.matias.timetracking.project.domain.exception.DuplicatedProjectNameException
import com.matias.timetracking.project.domain.repository.ProjectRepository
import org.springframework.dao.DuplicateKeyException
import org.springframework.stereotype.Service

@Service
class CreateProjectUseCase(private val projectRepository: ProjectRepository) {
    fun execute(request: CreateProjectCommand): CreateProjectResponse =
        request
            .createProjectFromRequest()
            .runCatching { this.save() }
            .fold(
                onSuccess = { CreateProjectResponse(it.id) },
                onFailure = { e ->
                    when(e) {
                        is DuplicateKeyException -> throw DuplicatedProjectNameException(request.name)
                        else -> throw e
                    }
                }
            )

    private fun CreateProjectCommand.createProjectFromRequest() =
        Project.createNewProject(
            name,
            description,
            categoryId
        )

    private fun Project.save() = apply { projectRepository.save(this) }
}