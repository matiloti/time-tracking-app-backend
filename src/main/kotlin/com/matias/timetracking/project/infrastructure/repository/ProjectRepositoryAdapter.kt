package com.matias.timetracking.project.infrastructure.repository

import com.matias.timetracking.project.domain.aggregate.Milestone
import com.matias.timetracking.project.domain.aggregate.Project
import com.matias.timetracking.project.domain.repository.ProjectRepository
import com.matias.timetracking.project.infrastructure.dao.MilestoneDao
import com.matias.timetracking.project.infrastructure.dao.ProjectDao
import com.matias.timetracking.project.infrastructure.dao.row.MilestoneRow
import com.matias.timetracking.project.infrastructure.dao.row.ProjectRow
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.sql.Timestamp
import java.util.*

@Repository
class ProjectRepositoryAdapter(
    val jdbc: NamedParameterJdbcTemplate,
    val projectDao: ProjectDao,
    val milestoneDao: MilestoneDao
): ProjectRepository {

    @Transactional
    override fun save(project: Project) {
        jdbc.update(
            """
                INSERT INTO projects (
                    id,
                    name,
                    description,
                    category_id,
                    created_at
                ) VALUES (:id,:name,:description,:categoryId,:createdAt)
                ON CONFLICT (id)
                DO UPDATE SET
                    name = EXCLUDED.name,
                    description = EXCLUDED.description,
                    category_id = EXCLUDED.category_id
            """.trimIndent(), mapOf(
                "id" to project.id,
                "name" to project.name,
                "description" to project.description,
                "categoryId" to project.categoryId,
                "createdAt" to Timestamp.valueOf(project.createdAt)
            )
        )

        jdbc.batchUpdate(
            """
            INSERT INTO milestones (
                id,
                project_id,
                name,
                description,
                start_date,
                end_date,
                created_at,
                updated_at
            ) VALUES (:id,:projectId,:name,:description,:startDate,:endDate,:createdAt,:updatedAt)
                ON CONFLICT (id)
                DO UPDATE SET
                    name = EXCLUDED.name,
                    description = EXCLUDED.description,
                    start_date = EXCLUDED.start_date,
                    end_date = EXCLUDED.end_date,
                    updated_at = EXCLUDED.updated_at
            """.trimIndent(),
            project.milestones().map { mapOf(
                "id" to it.id,
                "projectId" to it.projectId,
                "name" to it.name,
                "description" to it.description,
                "startDate" to it.startDate,
                "endDate" to it.endDate,
                "createdAt" to it.createdAt,
                "updatedAt" to it.updatedAt
            ) }.toTypedArray()
        )
    }

    override fun findById(projectId: UUID): Project =
        projectDao
            .findById(projectId)
            .createDomainObject(milestoneDao.findAllByProjectId(projectId))

    override fun findAll(): List<Project> =
        projectDao
            .findAll()
            .map { it.createDomainObject(milestoneDao.findAllByProjectId(it.id)) }

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