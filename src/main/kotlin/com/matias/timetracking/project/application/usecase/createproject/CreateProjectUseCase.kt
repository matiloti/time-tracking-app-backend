package com.matias.timetracking.project.application.usecase.createproject

import com.matias.timetracking.project.domain.aggregate.Project
import com.matias.timetracking.project.domain.enums.Category
import com.matias.timetracking.project.domain.exception.DuplicatedProjectNameException
import com.matias.timetracking.project.domain.repository.ProjectRepository
import org.springframework.dao.DuplicateKeyException
import org.springframework.stereotype.Service

@Service
class CreateProjectUseCase(private val projectRepository: ProjectRepository) {
    fun execute(command: CreateProjectCommand): CreateProjectResponse =
        command
            .createProjectFromRequest()
            .runCatching { this.save() }
            .fold(
                onSuccess = { CreateProjectResponse(it.id!!) },
                onFailure = { e ->
                    when(e) {
                        is DuplicateKeyException -> throw DuplicatedProjectNameException(command.name)
                        else -> throw e
                    }
                }
            )

    private fun CreateProjectCommand.createProjectFromRequest() =
        Project.create(
            name,
            description,
            Category.parse(categoryId)
        )

    private fun Project.save() = projectRepository.save(this)
}