package com.matias.timetracking.project.application.usecase.createmilestone

import com.matias.timetracking.project.domain.repository.ProjectRepository
import org.springframework.stereotype.Service

@Service
class CreateMilestoneUseCase(val projectRepository: ProjectRepository) {
    fun execute(request: CreateMilestoneCommand): CreateMilestoneResponse? =
        projectRepository
            .findById(request.projectId)
            ?.let { project ->
                val newMilestone = project
                    .createNewMilestone(
                        request.name,
                        request.description,
                        request.startDate,
                        request.endDate
                    )
                projectRepository.save(project)
                CreateMilestoneResponse(newMilestone.id)
            }
}