package com.matias.timetracking.project.infrastructure.repository

import com.matias.timetracking.project.domain.aggregate.Project
import com.matias.timetracking.project.domain.repository.ProjectRepository
import com.matias.timetracking.project.infrastructure.dao.JdbcMilestoneDao
import com.matias.timetracking.project.infrastructure.dao.MilestoneDao
import com.matias.timetracking.project.infrastructure.dao.ProjectDao
import com.matias.timetracking.project.infrastructure.dao.row.MilestoneRow
import com.matias.timetracking.project.infrastructure.dao.row.ProjectRow
import com.matias.timetracking.project.infrastructure.repository.mapper.ProjectMapper.Companion.loadDomainObject
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
class ProjectRepositoryAdapter(
    private val projectDao: ProjectDao,
    private val milestoneDao: MilestoneDao,
    private val jdbcMilestoneDao: JdbcMilestoneDao
): ProjectRepository {

    @Transactional
    override fun save(project: Project): Project {
        val projectRow = ProjectRow(
            id = project.id,
            name = project.name,
            description = project.description,
            categoryId = project.categoryId,
            createdAt = project.createdAt,
            updatedAt = project.updatedAt()
        )
        projectDao.save(projectRow)

        val insertedMilestones = project.milestones().filter { it.id == null }.map { milestone ->
            milestoneDao.save(MilestoneRow(
                id = milestone.id,
                projectId = milestone.projectId,
                name = milestone.name,
                description = milestone.description,
                startDate = milestone.startDate,
                endDate = milestone.endDate,
                createdAt = milestone.createdAt,
                updatedAt = milestone.updatedAt()
            ))
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

        return projectRow.loadDomainObject(insertedMilestones)
    }

    override fun findById(projectId: UUID): Project? =
        projectDao
            .findByIdOrNull(projectId)
            ?.loadDomainObject(milestoneDao.findByProjectId(projectId))

    override fun findAll(): List<Project> {
        val projects = projectDao.findAll()
        val projectIds = projects.mapNotNull { it.id }
        val milestonesByProjectId = milestoneDao.findByProjectIdIn(projectIds).groupBy { it.projectId }
        return projects.map { it.loadDomainObject(milestonesByProjectId[it.id] ?: emptyList()) }
    }
}