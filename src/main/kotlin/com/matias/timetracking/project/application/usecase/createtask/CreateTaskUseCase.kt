package com.matias.timetracking.project.application.usecase.createtask

import com.matias.timetracking.project.domain.exception.MilestoneIdNotFoundException
import com.matias.timetracking.project.domain.repository.ProjectRepository
import org.springframework.stereotype.Service

@Service
class CreateTaskUseCase(private val projectRepository: ProjectRepository) {
    fun execute(command: CreateTaskCommand) =
        projectRepository
            .findByMilestoneId(command.milestoneId)
            ?.let { project ->
                project.addTaskToMilestone(
                    command.name,
                    command.description,
                    command.priority,
                    command.milestoneId
                )
                val savedProject = projectRepository.save(project)
                CreateTaskResponse(
                    savedProject
                        .milestoneTasks(command.milestoneId)!!
                        .maxWithOrNull { t1, t2 -> if (t1.createdAt >= t2.createdAt) 1 else 0 }!!
                        .id()!!
                )
            } ?: throw MilestoneIdNotFoundException(command.milestoneId)
}