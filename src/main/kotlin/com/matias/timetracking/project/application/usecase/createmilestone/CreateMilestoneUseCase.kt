package com.matias.timetracking.project.application.usecase.createmilestone

import com.matias.timetracking.project.domain.aggregate.Milestone
import com.matias.timetracking.project.domain.repository.ProjectRepository
import com.matias.timetracking.project.domain.aggregate.Project
import com.matias.timetracking.project.domain.repository.MilestoneRepository
import java.time.LocalDateTime
import java.util.UUID

class CreateMilestoneUseCase(val milestoneRepository: MilestoneRepository) {
    fun execute(request: CreateMilestoneCommand): CreateMilestoneResponse {
        val milestone = request.mapToDomain()
        milestoneRepository.save(milestone)
        return CreateMilestoneResponse(milestone.id)
    }

    fun CreateMilestoneCommand.mapToDomain() = Milestone(
        UUID.randomUUID(),
        projectId,
        name,
        description,
        startDate,
        endDate,
        LocalDateTime.now(),
        LocalDateTime.now()
    )
}