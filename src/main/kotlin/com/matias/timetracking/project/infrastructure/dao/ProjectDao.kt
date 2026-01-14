package com.matias.timetracking.project.infrastructure.dao

import com.matias.timetracking.project.infrastructure.dao.row.ProjectRow
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class ProjectDao(val jdbc: NamedParameterJdbcTemplate) {

    fun findById(projectId: UUID): ProjectRow =
        jdbc.queryForObject<ProjectRow>(
            "SELECT * FROM projects WHERE id = :id",
            mapOf("id" to projectId)
        ) { rs, _ ->
            ProjectRow(
                id = UUID.fromString(rs.getString("id")),
                name = rs.getString("name"),
                description = rs.getString("description"),
                categoryId = rs.getInt("category_id"),
                createdAt = rs.getTimestamp("created_at").toLocalDateTime()
            )
        }

    fun findAll(): List<ProjectRow> =
        jdbc.query(
            "SELECT * FROM projects ORDER BY created_at DESC"
        ) { rs, _ ->
            ProjectRow(
                id = UUID.fromString(rs.getString("id")),
                name = rs.getString("name"),
                description = rs.getString("description"),
                categoryId = rs.getInt("category_id"),
                createdAt = rs.getTimestamp("created_at").toLocalDateTime()
            )
        }
}