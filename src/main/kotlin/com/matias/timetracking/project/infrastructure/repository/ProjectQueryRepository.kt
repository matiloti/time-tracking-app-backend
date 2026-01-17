package com.matias.timetracking.project.infrastructure.repository

import com.matias.timetracking.project.infrastructure.controller.getprojectdetails.MilestoneItem
import com.matias.timetracking.project.infrastructure.controller.getprojectdetails.ProjectDetailsDto
import com.matias.timetracking.project.infrastructure.controller.listallprojects.ProjectListItemResponse
import com.matias.timetracking.project.infrastructure.dao.MilestoneDao
import com.matias.timetracking.project.infrastructure.dao.ProjectDao
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class ProjectQueryRepository(
    val projectDao: ProjectDao,
    val milestoneDao: MilestoneDao
) {

    fun getProjectDetailsById(projectId: UUID): ProjectDetailsDto? =
        projectDao
            .findByIdOrNull(projectId)
            ?.let { projectRow ->
                ProjectDetailsDto(
                    id = projectRow.id,
                    name = projectRow.name,
                    description = projectRow.description,
                    categoryId = projectRow.categoryId,
                    createdAt = projectRow.createdAt,
                    milestones = milestoneDao
                        .findByProjectId(projectId)
                        .map { milestoneRow -> MilestoneItem(
                            id = milestoneRow.id,
                            name = milestoneRow.name,
                            startDate = milestoneRow.startDate,
                            endDate = milestoneRow.endDate)
                        }
                )
            }

    fun listAllProjects(): List<ProjectListItemResponse> =
        projectDao.findAll().map {
            ProjectListItemResponse(
                it.id,
                it.name,
                it.description,
                it.categoryId
            )
        }
}