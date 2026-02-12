package com.matias.timetracking.project.infrastructure.repository.query

import com.matias.timetracking.project.infrastructure.controller.getmilestonedetails.MilestoneDetailsDto
import com.matias.timetracking.project.infrastructure.controller.getmilestonedetails.TaskItem
import com.matias.timetracking.project.infrastructure.dao.MilestoneDao
import com.matias.timetracking.project.infrastructure.dao.ProjectDao
import com.matias.timetracking.project.infrastructure.dao.TaskDao
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class MilestoneQueryRepository(val projectDao: ProjectDao, val milestoneDao: MilestoneDao, val taskDao: TaskDao) {
    fun getMilestoneDetailsById(milestoneId: UUID) =
        milestoneDao
            .findByIdOrNull(milestoneId)
            ?.let { milestoneRow ->
                projectDao
                    .findByIdOrNull(milestoneRow.projectId)
                    ?.let { projectRow ->
                        val tasks = taskDao.findAllByMilestoneId(milestoneId)
                        MilestoneDetailsDto(
                            id = milestoneRow.id,
                            name = milestoneRow.name,
                            description = milestoneRow.description,
                            startDate = milestoneRow.startDate,
                            endDate = milestoneRow.endDate,
                            projectId = projectRow.id,
                            projectName = projectRow.name,
                            tasks = tasks.map {
                                TaskItem(
                                    id = it.id,
                                    name = it.name,
                                    description = it.description,
                                    priorityId = it.priorityId,
                                    completed = it.completed,
                                )
                            },
                        )
                    }
            }
}
