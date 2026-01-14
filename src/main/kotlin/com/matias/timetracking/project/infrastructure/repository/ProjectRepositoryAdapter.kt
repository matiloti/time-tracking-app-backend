package com.matias.timetracking.project.infrastructure.repository

import com.matias.timetracking.project.domain.aggregate.Project
import com.matias.timetracking.project.domain.repository.ProjectRepository
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.sql.Timestamp
import java.util.*

@Repository
class ProjectRepositoryAdapter(val jdbc: NamedParameterJdbcTemplate): ProjectRepository {

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
    }

    override fun findAll(): List<Project> =
        jdbc.query(
            "SELECT * FROM projects ORDER BY created_at DESC",
            RowMapper { rs, _ ->
                Project(
                    id = UUID.fromString(rs.getString("id")),
                    name = rs.getString("name"),
                    description = rs.getString("description"),
                    categoryId = rs.getInt("category_id"),
                    createdAt = rs.getTimestamp("created_at").toLocalDateTime()
                )
            }
        )

}