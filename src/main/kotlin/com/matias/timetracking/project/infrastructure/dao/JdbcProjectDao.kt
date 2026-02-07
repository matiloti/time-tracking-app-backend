package com.matias.timetracking.project.infrastructure.dao

import com.matias.timetracking.project.infrastructure.dao.row.ProjectRow
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class JdbcProjectDao(private val jdbc: NamedParameterJdbcTemplate) {
    fun upsert(project: ProjectRow) = with(project) {
        jdbc.update(
            """
            INSERT INTO projects (id, name, description, category_id, created_at, updated_at)
            VALUES (:id, :name, :description, :categoryId, :createdAt, :updatedAt)
            ON CONFLICT (id) DO UPDATE SET
                name = EXCLUDED.name,
                description = EXCLUDED.description,
                category_id = EXCLUDED.category_id,
                updated_at = EXCLUDED.updated_at
            """.trimIndent(),
            mapOf(
                "id" to id,
                "name" to name,
                "description" to description,
                "categoryId" to categoryId,
                "createdAt" to createdAt,
                "updatedAt" to updatedAt,
            ),
        )
    }
}
