package com.matias.timetracking.project.application.usecase.createmilestone

import com.matias.timetracking.project.domain.aggregate.Project
import com.matias.timetracking.project.domain.exception.ProjectIdNotFoundException
import com.matias.timetracking.project.domain.repository.ProjectRepository
import org.springframework.stereotype.Service

@Service
class CreateMilestoneUseCase(private val projectRepository: ProjectRepository) {
    fun execute(command: CreateMilestoneCommand): CreateMilestoneResponse =
        with(projectRepository.findById(command.projectId)) {
            this ?: throw ProjectIdNotFoundException(command.projectId)
            this.addMilestone(
                command.name,
                command.description,
                command.startDate,
                command.endDate,
            )
            this.save()
            CreateMilestoneResponse(
                this.milestones()
                    .maxWithOrNull { m1, m2 -> if (m1.createdAt >= m2.createdAt) 1 else 0 }!!.id,
            )
        }

    private fun Project.save() = projectRepository.save(this)
}
