package com.matias.timetracking.project.infrastructure.repository

import com.matias.timetracking.project.domain.aggregate.Project
import com.matias.timetracking.project.domain.repository.ProjectRepositoryPort
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.sql.Timestamp

@Repository
class ProjectRepositoryAdapter(val jdbc: NamedParameterJdbcTemplate): ProjectRepositoryPort {

    @Transactional
    override fun save(project: Project) {
        jdbc.update(
            """
                INSERT INTO projects (
                    id,
                    title,
                    description,
                    category_id,
                    created_at
                ) VALUES (:id,:title,:description,:categoryId,:createdAt)
                ON CONFLICT (id)
                DO UPDATE SET
                    title = EXCLUDED.title,
                    description = EXCLUDED.description,
                    category_id = EXCLUDED.category_id
            """.trimIndent(), mapOf(
                "id" to project.id,
                "title" to project.title,
                "description" to project.description,
                "categoryId" to project.categoryId,
                "createdAt" to Timestamp.valueOf(project.createdAt)
            )
        )
    }

}