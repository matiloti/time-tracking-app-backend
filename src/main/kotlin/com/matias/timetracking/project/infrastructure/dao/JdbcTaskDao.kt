package com.matias.timetracking.project.infrastructure.dao

import com.matias.timetracking.project.infrastructure.dao.row.TaskRow
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class JdbcTaskDao(private val jdbc: NamedParameterJdbcTemplate) {

    fun upsert(task: TaskRow) = with(task) {
        jdbc.update(
            """
                INSERT INTO tasks (id, milestone_id, name, description, priority_id, completed, created_at, updated_at)
                VALUES (:id, :milestoneId, :name, :description, :priorityId, :completed, :createdAt, :updatedAt)
                ON CONFLICT (id) DO UPDATE SET
                    name = EXCLUDED.name,
                    description = EXCLUDED.description,
                    priority_id = EXCLUDED.priority_id,
                    completed = EXCLUDED.completed,
                    updated_at = EXCLUDED.updated_at
            """.trimIndent(),
            mapOf(
                "id" to id,
                "milestoneId" to milestoneId,
                "name" to name,
                "description" to description,
                "priorityId" to priorityId,
                "completed" to completed,
                "createdAt" to createdAt,
                "updatedAt" to updatedAt,
            ),
        )
    }

    fun batchUpsert(tasks: List<TaskRow>): IntArray = jdbc.batchUpdate(
        """
            INSERT INTO tasks (id, milestone_id, name, description, priority_id, completed, created_at, updated_at)
            VALUES (:id, :milestoneId, :name, :description, :priorityId, :completed, :createdAt, :updatedAt)
            ON CONFLICT (id) DO UPDATE SET
                name = EXCLUDED.name,
                description = EXCLUDED.description,
                priority_id = EXCLUDED.priority_id,
                completed = EXCLUDED.completed,
                updated_at = EXCLUDED.updated_at
        """.trimIndent(),
        tasks.map {
            mapOf(
                "id" to it.id,
                "milestoneId" to it.milestoneId,
                "name" to it.name,
                "description" to it.description,
                "priorityId" to it.priorityId,
                "completed" to it.completed,
                "createdAt" to it.createdAt,
                "updatedAt" to it.updatedAt,
            )
        }.toTypedArray(),
    )
}
