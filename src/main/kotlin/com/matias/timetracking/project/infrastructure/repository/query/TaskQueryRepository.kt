package com.matias.timetracking.project.infrastructure.repository.query

import com.matias.timetracking.project.infrastructure.controller.gettaskdetails.TaskDetailsResponse
import com.matias.timetracking.project.infrastructure.dao.MilestoneDao
import com.matias.timetracking.project.infrastructure.dao.TaskDao
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class TaskQueryRepository(val milestoneDao: MilestoneDao, val taskDao: TaskDao) {
    fun getTaskDetailsById(taskId: UUID) = taskDao
        .findByIdOrNull(taskId)
        ?.let { taskRow ->
            milestoneDao
                .findByIdOrNull(taskRow.milestoneId)
                ?.let { milestoneRow ->
                    TaskDetailsResponse(
                        id = taskRow.id,
                        name = taskRow.name,
                        description = taskRow.description,
                        priorityId = taskRow.priorityId,
                        completed = taskRow.completed,
                        milestoneId = milestoneRow.id,
                        milestoneName = milestoneRow.name,
                    )
                }
        }
}
