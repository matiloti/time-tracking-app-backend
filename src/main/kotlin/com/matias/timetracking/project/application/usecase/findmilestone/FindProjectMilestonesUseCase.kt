package com.matias.timetracking.project.application.usecase.findmilestone

import com.matias.timetracking.project.domain.aggregate.Milestone
import com.matias.timetracking.project.domain.repository.MilestoneRepository

class FindProjectMilestonesUseCase(val milestoneRepository: MilestoneRepository) {
    fun execute(findProjectMilestonesCommand: FindProjectMilestonesCommand): List<Milestone> {
        return milestoneRepository.findAllByProjectId(findProjectMilestonesCommand.projectId);
    }
}