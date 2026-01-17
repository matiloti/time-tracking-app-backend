package com.matias.timetracking.project.infrastructure.dao

import com.matias.timetracking.project.infrastructure.dao.row.MilestoneRow
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class JdbcMilestoneDao(private val jdbc: NamedParameterJdbcTemplate) {
    fun batchSave(milestones: List<MilestoneRow>): IntArray =
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
            milestones.map { mapOf(
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