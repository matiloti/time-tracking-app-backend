package com.matias.timetracking.project.infrastructure.repository

import com.matias.timetracking.project.infrastructure.controller.getprojectdetails.MilestoneItem
import com.matias.timetracking.project.infrastructure.controller.getprojectdetails.ProjectDetailsDto
import com.matias.timetracking.project.infrastructure.dao.MilestoneDao
import com.matias.timetracking.project.infrastructure.dao.ProjectDao
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class ProjectQueryRepository(
    val projectDao: ProjectDao,
    val milestoneDao: MilestoneDao
) {

    fun getProjectDetailsById(projectId: UUID): ProjectDetailsDto {
        val projectRow = projectDao.getProjectRow(projectId)
        val projectMilestoneRows = milestoneDao.findAllByProjectId(projectId)

        return ProjectDetailsDto(
            id = projectRow.id,
            name = projectRow.name,
            description = projectRow.description,
            categoryId = projectRow.categoryId,
            createdAt = projectRow.createdAt,
            milestones = projectMilestoneRows.map { m -> MilestoneItem(
                id = m.id,
                name = m.name,
                startDate = m.startDate,
                endDate = m.endDate
            ) }
        )
    }
}