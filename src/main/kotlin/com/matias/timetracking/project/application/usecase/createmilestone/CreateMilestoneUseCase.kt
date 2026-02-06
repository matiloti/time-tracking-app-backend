package com.matias.timetracking.project.application.usecase.createmilestone

import com.matias.timetracking.project.domain.exception.ProjectIdNotFoundException
import com.matias.timetracking.project.domain.repository.ProjectRepository
import org.springframework.stereotype.Service

@Service
class CreateMilestoneUseCase(private val projectRepository: ProjectRepository) {
    fun execute(command: CreateMilestoneCommand): CreateMilestoneResponse = projectRepository
        .findById(command.projectId)
        ?.let { project ->
            project
                .addMilestone(
                    command.name,
                    command.description,
                    command.startDate,
                    command.endDate,
                )
            val savedProject = projectRepository.save(project)
            CreateMilestoneResponse(
                savedProject
                    .milestones()
                    .maxWithOrNull { m1, m2 -> if (m1.createdAt >= m2.createdAt) 1 else 0 }
                    ?.id!!,
            )
        } ?: throw ProjectIdNotFoundException(command.projectId)
}
