package com.matias.timetracking.project.infrastructure.repository.mapper

import com.matias.timetracking.project.domain.aggregate.Project
import com.matias.timetracking.project.domain.entity.Milestone
import com.matias.timetracking.project.infrastructure.dao.row.MilestoneRow
import com.matias.timetracking.project.infrastructure.dao.row.ProjectRow

class ProjectMapper {
    companion object {
        fun ProjectRow.loadDomainObject(milestones: List<MilestoneRow>) =
            Project.load(
                id!!,
                name,
                description,
                categoryId,
                createdAt,
                updatedAt,
                milestones.map {
                    Milestone.load(
                        it.id!!,
                        it.projectId,
                        it.name,
                        it.description,
                        it.startDate,
                        it.endDate,
                        it.createdAt,
                        it.updatedAt
                    )
                }.toMutableList()
            )
    }
}