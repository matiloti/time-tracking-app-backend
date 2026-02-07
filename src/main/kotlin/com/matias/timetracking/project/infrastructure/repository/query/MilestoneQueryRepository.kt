package com.matias.timetracking.project.infrastructure.repository.query

import com.matias.timetracking.project.infrastructure.controller.getmilestonedetails.MilestoneDetailsDto
import com.matias.timetracking.project.infrastructure.dao.MilestoneDao
import com.matias.timetracking.project.infrastructure.dao.ProjectDao
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class MilestoneQueryRepository(val projectDao: ProjectDao, val milestoneDao: MilestoneDao) {
    fun getMilestoneDetailsById(milestoneId: UUID) = milestoneDao
        .findByIdOrNull(milestoneId)
        ?.let { milestoneRow ->
            projectDao
                .findByIdOrNull(milestoneRow.projectId)
                ?.let { projectRow ->
                    MilestoneDetailsDto(
                        id = milestoneRow.id,
                        name = milestoneRow.name,
                        description = milestoneRow.description,
                        startDate = milestoneRow.startDate,
                        endDate = milestoneRow.endDate,
                        projectId = projectRow.id,
                        projectName = projectRow.name,
                    )
                }
        }
}
