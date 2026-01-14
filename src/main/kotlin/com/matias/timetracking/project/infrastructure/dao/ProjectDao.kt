package com.matias.timetracking.project.infrastructure.dao

import com.matias.timetracking.project.infrastructure.dao.row.ProjectRow
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class ProjectDao(val jdbc: NamedParameterJdbcTemplate) {

    fun getProjectRow(id: UUID): ProjectRow =
        jdbc.queryForObject<ProjectRow>(
            """
                SELECT *
                FROM projects p
                    JOIN milestones m ON p.id = m.project_id
                WHERE p.id = :id
            """.trimIndent(),
            mapOf("id" to id.toString()),
            RowMapper { rs, _ ->
                ProjectRow(
                    id = UUID.fromString(rs.getString("id")),
                    name = rs.getString("name"),
                    description = rs.getString("description"),
                    categoryId = rs.getInt("category_id"),
                    createdAt = rs.getTimestamp("created_at").toLocalDateTime()
                )
            }
        )
}