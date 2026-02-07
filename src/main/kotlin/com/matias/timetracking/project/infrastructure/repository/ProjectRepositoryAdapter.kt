package com.matias.timetracking.project.infrastructure.repository

import com.matias.timetracking.project.domain.aggregate.Project
import com.matias.timetracking.project.domain.repository.ProjectRepository
import com.matias.timetracking.project.infrastructure.dao.JdbcMilestoneDao
import com.matias.timetracking.project.infrastructure.dao.JdbcProjectDao
import com.matias.timetracking.project.infrastructure.dao.JdbcTaskDao
import com.matias.timetracking.project.infrastructure.dao.MilestoneDao
import com.matias.timetracking.project.infrastructure.dao.ProjectDao
import com.matias.timetracking.project.infrastructure.dao.TaskDao
import com.matias.timetracking.project.infrastructure.dao.row.MilestoneRow
import com.matias.timetracking.project.infrastructure.dao.row.ProjectRow
import com.matias.timetracking.project.infrastructure.dao.row.TaskRow
import com.matias.timetracking.project.infrastructure.repository.mapper.ProjectMapper.loadDomainObject
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
class ProjectRepositoryAdapter(
    private val projectDao: ProjectDao,
    private val jdbcProjectDao: JdbcProjectDao,
    private val milestoneDao: MilestoneDao,
    private val jdbcMilestoneDao: JdbcMilestoneDao,
    private val taskDao: TaskDao,
    private val jdbcTaskDao: JdbcTaskDao,
) : ProjectRepository {

    @Transactional
    override fun save(project: Project) {
        jdbcProjectDao.upsert(
            ProjectRow(
                id = project.id,
                name = project.name,
                description = project.description,
                categoryId = project.category.id,
                createdAt = project.createdAt,
                updatedAt = project.updatedAt(),
            ),
        )

        jdbcMilestoneDao.batchUpsert(
            project.milestones().map { milestone ->
                MilestoneRow(
                    id = milestone.id,
                    projectId = milestone.projectId,
                    name = milestone.name,
                    description = milestone.description,
                    startDate = milestone.startDate,
                    endDate = milestone.endDate,
                    createdAt = milestone.createdAt,
                    updatedAt = milestone.updatedAt(),
                )
            },
        )

        jdbcTaskDao.batchUpsert(
            project.allMilestoneTasks().map { task ->
                TaskRow(
                    id = task.id,
                    milestoneId = task.milestoneId,
                    name = task.name,
                    description = task.description,
                    priorityId = task.priority.id,
                    completed = task.completed,
                    createdAt = task.createdAt,
                    updatedAt = task.updatedAt(),
                )
            },
        )
    }

    override fun findById(projectId: UUID): Project? = projectDao
        .findByIdOrNull(projectId)
        ?.let { projectRow ->
            val milestoneRows = milestoneDao.findByProjectId(projectId)
            val taskRows = taskDao.findAllByProjectId(projectId)
            loadDomainObject(projectRow, milestoneRows, taskRows)
        }

    override fun findByMilestoneId(milestoneId: UUID): Project? = milestoneDao
        .getMilestoneProjectId(milestoneId)
        ?.let { projectId ->
            this.findById(projectId)
        }
}
