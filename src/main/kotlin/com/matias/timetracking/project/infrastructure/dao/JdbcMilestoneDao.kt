package com.matias.timetracking.project.infrastructure.dao

import com.matias.timetracking.project.infrastructure.dao.row.MilestoneRow
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class JdbcMilestoneDao(private val jdbc: NamedParameterJdbcTemplate) {
    fun batchUpdate(milestones: List<MilestoneRow>): IntArray =
        jdbc.batchUpdate(
            """
            UPDATE milestones SET
                name = :name,
                description = :description,
                start_date = :startDate,
                end_date = :endDate,
                updated_at = :updatedAt
            WHERE id = :id
            """.trimIndent(),
            milestones.map { mapOf(
                "id" to it.id,
                "name" to it.name,
                "description" to it.description,
                "startDate" to it.startDate,
                "endDate" to it.endDate,
                "updatedAt" to it.updatedAt
            ) }.toTypedArray()
        )
}