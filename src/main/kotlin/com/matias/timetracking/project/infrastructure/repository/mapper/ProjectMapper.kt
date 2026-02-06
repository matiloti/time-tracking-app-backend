package com.matias.timetracking.project.infrastructure.repository.mapper

import com.matias.timetracking.project.domain.aggregate.Project
import com.matias.timetracking.project.domain.entity.Milestone
import com.matias.timetracking.project.domain.entity.Task
import com.matias.timetracking.project.domain.enums.Category
import com.matias.timetracking.project.infrastructure.dao.row.MilestoneRow
import com.matias.timetracking.project.infrastructure.dao.row.ProjectRow
import com.matias.timetracking.project.infrastructure.dao.row.TaskRow

class ProjectMapper {
    companion object {
        fun loadDomainObject(projectRow: ProjectRow, milestoneRows: List<MilestoneRow>, taskRows: List<TaskRow>): Project {
            val tasksByMilestone = taskRows.groupBy { it.milestoneId }
            return Project.load(
                projectRow.id!!,
                projectRow.name,
                projectRow.description,
                Category.parse(projectRow.categoryId),
                projectRow.createdAt,
                projectRow.updatedAt,
                milestoneRows.map { milestoneRow ->
                    Milestone.load(
                        id = milestoneRow.id!!,
                        projectId = milestoneRow.projectId,
                        name = milestoneRow.name,
                        description = milestoneRow.description,
                        startDate = milestoneRow.startDate,
                        endDate = milestoneRow.endDate,
                        createdAt = milestoneRow.createdAt,
                        updatedAt = milestoneRow.updatedAt,
                        tasks = (tasksByMilestone[milestoneRow.id!!] ?: mutableListOf()).map { taskRow ->
                            Task.load(
                                id = taskRow.id!!,
                                milestoneId = taskRow.milestoneId,
                                name = taskRow.name,
                                description = taskRow.description,
                                priorityValue = taskRow.priorityId,
                                completed = taskRow.completed,
                                createdAt = taskRow.createdAt,
                                updatedAt = taskRow.updatedAt
                            )
                        }.toMutableList()
                    )
                }.toMutableList()
            )
        }
    }
}