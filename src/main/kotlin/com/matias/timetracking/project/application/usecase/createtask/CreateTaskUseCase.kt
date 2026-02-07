package com.matias.timetracking.project.application.usecase.createtask

import com.matias.timetracking.project.domain.aggregate.Project
import com.matias.timetracking.project.domain.exception.MilestoneIdNotFoundException
import com.matias.timetracking.project.domain.repository.ProjectRepository
import org.springframework.stereotype.Service

@Service
class CreateTaskUseCase(private val projectRepository: ProjectRepository) {
    fun execute(command: CreateTaskCommand) =
        with(projectRepository.findByMilestoneId(command.milestoneId)) {
            this ?: throw MilestoneIdNotFoundException(command.milestoneId)
            this.addTaskToMilestone(
                command.name,
                command.description,
                command.priority,
                command.milestoneId,
            )
            this.save()
            CreateTaskResponse(
                this
                    .milestoneTasks(command.milestoneId)!!
                    .maxWithOrNull { t1, t2 -> if (t1.createdAt >= t2.createdAt) 1 else 0 }!!.id,
            )
        }

    private fun Project.save() = projectRepository.save(this)
}
