package com.matias.timetracking.project.infrastructure.repository

import com.matias.timetracking.project.domain.aggregate.Project
import com.matias.timetracking.project.domain.repository.ProjectRepository
import com.matias.timetracking.project.infrastructure.dao.*
import com.matias.timetracking.project.infrastructure.dao.row.MilestoneRow
import com.matias.timetracking.project.infrastructure.dao.row.ProjectRow
import com.matias.timetracking.project.infrastructure.dao.row.TaskRow
import com.matias.timetracking.project.infrastructure.repository.mapper.ProjectMapper.Companion.loadDomainObject
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
class ProjectRepositoryAdapter(
    private val projectDao: ProjectDao,
    private val milestoneDao: MilestoneDao,
    private val jdbcMilestoneDao: JdbcMilestoneDao,
    private val taskDao: TaskDao,
    private val jdbcTaskDao: JdbcTaskDao
): ProjectRepository {

    @Transactional
    override fun save(project: Project): Project {
        val savedProjectRow = ProjectRow(
            id = project.id,
            name = project.name,
            description = project.description,
            categoryId = project.category.id,
            createdAt = project.createdAt,
            updatedAt = project.updatedAt()
        )
        projectDao.save(savedProjectRow)

        val savedMilestonesRows = saveMilestones(project)
        val savedTasksRows = saveTasks(project)

        return loadDomainObject(savedProjectRow, savedMilestonesRows, savedTasksRows)
    }

    private fun saveTasks(project: Project): MutableList<TaskRow> {
        val insertedTasks = project.allMilestoneTasks().filter { it.id() == null }.map { task ->
            taskDao.save(
                TaskRow(
                    id = task.id(),
                    milestoneId = task.milestoneId,
                    name = task.name,
                    description = task.description,
                    priorityId = task.priority.id,
                    completed = task.completed,
                    createdAt = task.createdAt,
                    updatedAt = task.updatedAt
                )
            )
        }.toMutableList()

        val updatedTasks = project.allMilestoneTasks().filter { it.id() != null }.map { task ->
            TaskRow(
                id = task.id(),
                milestoneId = task.milestoneId,
                name = task.name,
                description = task.description,
                priorityId = task.priority.id,
                completed = task.completed,
                createdAt = task.createdAt,
                updatedAt = task.updatedAt
            )
        }
        jdbcTaskDao.batchUpdate(updatedTasks)

        insertedTasks.addAll(updatedTasks)

        return insertedTasks
    }

    private fun saveMilestones(project: Project): List<MilestoneRow> {
        val insertedMilestones = project.milestones().filter { it.id == null }.map { milestone ->
            milestoneDao.save(
                MilestoneRow(
                    id = milestone.id,
                    projectId = milestone.projectId,
                    name = milestone.name,
                    description = milestone.description,
                    startDate = milestone.startDate,
                    endDate = milestone.endDate,
                    createdAt = milestone.createdAt,
                    updatedAt = milestone.updatedAt()
                )
            )
        }.toMutableList()

        val updatedMilestones = project.milestones().filter { it.id != null }.map { milestone ->
            MilestoneRow(
                id = milestone.id,
                projectId = milestone.projectId,
                name = milestone.name,
                description = milestone.description,
                startDate = milestone.startDate,
                endDate = milestone.endDate,
                createdAt = milestone.createdAt,
                updatedAt = milestone.updatedAt()
            )
        }
        jdbcMilestoneDao.batchUpdate(updatedMilestones)
        insertedMilestones.addAll(updatedMilestones)

        return insertedMilestones
    }

    override fun findById(projectId: UUID): Project? =
        projectDao
            .findByIdOrNull(projectId)
            ?.let { projectRow ->
                val milestoneRows = milestoneDao.findByProjectId(projectId)
                val taskRows = taskDao.findAllByProjectId(projectId)
                loadDomainObject(projectRow, milestoneRows, taskRows)
            }

    override fun findByMilestoneId(milestoneId: UUID): Project? =
        milestoneDao
            .getMilestoneProjectId(milestoneId)
            ?.let { projectId ->
                this.findById(projectId)
            }
}