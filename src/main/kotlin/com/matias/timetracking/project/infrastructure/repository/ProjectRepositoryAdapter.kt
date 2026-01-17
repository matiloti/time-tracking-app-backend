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
    override fun save(project: Project) {
        projectDao.save(ProjectRow(
            project.id,
            project.name,
            project.description,
            project.categoryId,
            project.createdAt,
            project.updatedAt,
            isNewEntity = true
        ))

        jdbcMilestoneDao.batchSave(
            project.milestones().map { milestone ->
                MilestoneRow(
                    milestone.id,
                    milestone.projectId,
                    milestone.name,
                    milestone.description,
                    milestone.startDate,
                    milestone.endDate,
                    milestone.createdAt,
                    milestone.updatedAt,
                    isNewEntity = true
                )
            }
        )

    }

    override fun findById(projectId: UUID): Project? =
        projectDao
            .findByIdOrNull(projectId)
            ?.loadDomainObject(milestoneDao.findByProjectId(projectId))

    override fun findAll(): List<Project> {
        val projects = projectDao.findAll()
        val projectIds = projects.map { it.id }
        val milestonesByProjectId = milestoneDao.findByProjectIdIn(projectIds).groupBy { it.projectId }
        return projects.map { it.loadDomainObject(milestonesByProjectId[it.id] ?: emptyList()) }
    }



}