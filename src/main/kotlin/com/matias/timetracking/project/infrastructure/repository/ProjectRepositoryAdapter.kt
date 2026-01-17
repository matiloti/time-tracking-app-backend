package com.matias.timetracking.project.infrastructure.repository

import com.matias.timetracking.project.domain.aggregate.Milestone
import com.matias.timetracking.project.domain.aggregate.Project
import com.matias.timetracking.project.domain.repository.ProjectRepository
import com.matias.timetracking.project.infrastructure.dao.JdbcMilestoneDao
import com.matias.timetracking.project.infrastructure.dao.MilestoneDao
import com.matias.timetracking.project.infrastructure.dao.ProjectDao
import com.matias.timetracking.project.infrastructure.dao.row.MilestoneRow
import com.matias.timetracking.project.infrastructure.dao.row.ProjectRow
import org.springframework.data.repository.findByIdOrNull
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
class ProjectRepositoryAdapter(
    val jdbc: NamedParameterJdbcTemplate,
    val projectDao: ProjectDao,
    val milestoneDao: MilestoneDao,
    val jdbcMilestoneDao: JdbcMilestoneDao
): ProjectRepository {

    @Transactional
    override fun save(project: Project) {
        projectDao.save(ProjectRow(
            project.id,
            project.name,
            project.description,
            project.categoryId,
            project.createdAt,
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
                    milestone.updatedAt
                )
            }
        )

    }

    override fun findById(projectId: UUID): Project? =
        projectDao
            .findByIdOrNull(projectId)
            ?.createDomainObject(milestoneDao.findByProjectId(projectId))

    override fun findAll(): List<Project> =
        projectDao
            .findAll()
            .map { it.createDomainObject(milestoneDao.findByProjectId(it.id)) }

    fun ProjectRow.createDomainObject(milestones: List<MilestoneRow>) =
        Project(
            id,
            name,
            description,
            categoryId,
            createdAt,
            milestones =
                milestones.map {
                    Milestone(
                        it.id,
                        it.projectId,
                        it.name,
                        it.description,
                        it.startDate,
                        it.endDate,
                        it.createdAt,
                        it.updatedAt
                    )
                }.toMutableList()
    )

}