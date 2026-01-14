package com.matias.timetracking.project.infrastructure.repository

import com.matias.timetracking.project.domain.aggregate.Milestone
import com.matias.timetracking.project.domain.repository.MilestoneRepository
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.sql.Timestamp

@Repository
class MilestoneRepositoryAdapter(val jdbc: NamedParameterJdbcTemplate): MilestoneRepository {

    @Transactional
    override fun save(milestone: Milestone) {
        jdbc.update(
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
            """.trimIndent(), mapOf(
                "id" to milestone.id,
                "projectId" to milestone.projectId,
                "name" to milestone.name,
                "description" to milestone.description,
                "startDate" to Timestamp.valueOf(milestone.startDate),
                "endDate" to Timestamp.valueOf(milestone.endDate),
                "createdAt" to Timestamp.valueOf(milestone.createdAt),
                "updatedAt" to Timestamp.valueOf(milestone.updatedAt),
            )
        )
    }

}