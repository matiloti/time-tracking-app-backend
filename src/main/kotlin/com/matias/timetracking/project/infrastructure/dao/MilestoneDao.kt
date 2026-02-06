package com.matias.timetracking.project.infrastructure.dao

import com.matias.timetracking.project.infrastructure.dao.row.MilestoneRow
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.ListCrudRepository
import java.util.*

interface MilestoneDao : ListCrudRepository<MilestoneRow, UUID> {
    fun findByProjectId(projectId: UUID): List<MilestoneRow>

    @Query("SELECT project_id FROM milestones WHERE id = :milestoneId")
    fun getMilestoneProjectId(milestoneId: UUID): UUID?
}
