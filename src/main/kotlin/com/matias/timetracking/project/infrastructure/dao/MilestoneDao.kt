package com.matias.timetracking.project.infrastructure.dao

import com.matias.timetracking.project.infrastructure.dao.row.MilestoneRow
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class MilestoneDao(val jdbc: NamedParameterJdbcTemplate) {

    fun findAllByProjectId(projectId: UUID): List<MilestoneRow> =
        jdbc.query(
            """
                SELECT 
                    m.id,
                    m.project_id,
                    m.name,
                    m.description,
                    m.start_date,
                    m.end_date,
                    m.created_at,
                    m.updated_at
                FROM milestones m
                    JOIN projects p ON m.project_id = p.id
                WHERE m.project_id = :projectId
                ORDER BY m.start_date DESC""".trimIndent(),
            mapOf("projectId" to projectId),
            { rs, _ ->
                MilestoneRow(
                    id = UUID.fromString(rs.getString("id")),
                    projectId = UUID.fromString(rs.getString("project_id")),
                    name = rs.getString("name"),
                    description = rs.getString("description"),
                    startDate = rs.getTimestamp("start_date")?.toLocalDateTime(),
                    endDate = rs.getTimestamp("end_date")?.toLocalDateTime(),
                    createdAt = rs.getTimestamp("created_at").toLocalDateTime(),
                    updatedAt = rs.getTimestamp("updated_at").toLocalDateTime()
                )
            }
        )
}