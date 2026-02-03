package com.matias.timetracking.project.infrastructure.repository.query

import com.matias.timetracking.project.domain.Priority
import com.matias.timetracking.project.infrastructure.controller.getmilestonedetails.MilestoneDetailsDto
import com.matias.timetracking.project.infrastructure.controller.gettaskdetails.PriorityDto
import com.matias.timetracking.project.infrastructure.controller.gettaskdetails.TaskDetailsDto
import com.matias.timetracking.project.infrastructure.dao.MilestoneDao
import com.matias.timetracking.project.infrastructure.dao.ProjectDao
import com.matias.timetracking.project.infrastructure.dao.TaskDao
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class TaskQueryRepository(
    val milestoneDao: MilestoneDao,
    val taskDao: TaskDao
) {
    fun getTaskDetailsById(taskId: UUID) =
        taskDao
            .findByIdOrNull(taskId)
            ?.let { taskRow ->
                milestoneDao
                    .findByIdOrNull(taskRow.milestoneId)
                    ?.let { milestoneRow ->
                        TaskDetailsDto(
                            id = taskRow.id,
                            name = taskRow.name,
                            description = taskRow.description,
                            priority = PriorityDto(
                                id = Priority.parse(taskRow.priorityId).value,
                                name = Priority.parse(taskRow.priorityId).name
                            ),
                            completed = taskRow.completed,
                            milestoneId = milestoneRow.id!!,
                            milestoneName = milestoneRow.name
                        )
                    }
            }

}