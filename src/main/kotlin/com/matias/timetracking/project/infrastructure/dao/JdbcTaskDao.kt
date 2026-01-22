package com.matias.timetracking.project.infrastructure.dao

import com.matias.timetracking.project.infrastructure.dao.row.TaskRow
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class JdbcTaskDao(private val jdbc: NamedParameterJdbcTemplate) {
    fun batchUpdate(tasks: List<TaskRow>): IntArray =
        jdbc.batchUpdate(
            """
            UPDATE tasks SET
                name = :name,
                description = :description,
                priority = :priority,
                completed = :completed,
                updated_at = :updatedAt
            WHERE id = :id
            """.trimIndent(),
            tasks.map { mapOf(
                "id" to it.id,
                "name" to it.name,
                "description" to it.description,
                "priority" to it.priorityId,
                "completed" to it.completed,
                "updatedAt" to it.updatedAt
            ) }.toTypedArray()
        )
}