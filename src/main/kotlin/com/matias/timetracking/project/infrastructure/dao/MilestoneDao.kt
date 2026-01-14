package com.matias.timetracking.project.infrastructure.dao

import com.matias.timetracking.project.infrastructure.dao.row.MilestoneRow
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class MilestoneDao(val jdbc: NamedParameterJdbcTemplate) {

    fun findAllByProjectId(projectId: UUID): List<MilestoneRow> =
        jdbc.query(
            """
                SELECT * 
                FROM milestones m
                    JOIN projects p ON milestones.project_id = projects.id
                WHERE p.id = :id
                ORDER BY start_date DESC""".trimIndent(),
            mapOf("id" to projectId.toString()),
            { rs, _ ->
                MilestoneRow(
                    id = UUID.fromString(rs.getString("id")),
                    projectId = UUID.fromString(rs.getString("project_id")),
                    name = rs.getString("name"),
                    description = rs.getString("description"),
                    startDate = rs.getTimestamp("start_date").toLocalDateTime(),
                    endDate = rs.getTimestamp("end_date").toLocalDateTime(),
                    createdAt = rs.getTimestamp("created_at").toLocalDateTime(),
                    updatedAt = rs.getTimestamp("updated_at").toLocalDateTime()
                )
            }
        )
}