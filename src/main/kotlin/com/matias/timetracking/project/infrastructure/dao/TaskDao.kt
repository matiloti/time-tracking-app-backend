package com.matias.timetracking.project.infrastructure.dao

import com.matias.timetracking.project.infrastructure.dao.row.TaskRow
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.ListCrudRepository
import java.util.*

interface TaskDao : ListCrudRepository<TaskRow, UUID> {
    @Query(
        """
        SELECT
            t.id,
            t.milestone_id,
            t.name,
            t.description,
            t.priority_id,
            t.completed,
            t.created_at,
            t.updated_at
        FROM tasks t JOIN milestones m ON t.milestone_id = m.id
        WHERE m.project_id = :projectId
    """,
    )
    fun findAllByProjectId(projectId: UUID): List<TaskRow>
}
