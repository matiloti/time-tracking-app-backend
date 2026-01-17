package com.matias.timetracking.project.infrastructure.dao

import com.matias.timetracking.project.infrastructure.dao.row.MilestoneRow
import org.springframework.data.repository.ListCrudRepository
import java.util.*

interface MilestoneDao: ListCrudRepository<MilestoneRow, UUID> {
    fun findByProjectId(projectId: UUID): List<MilestoneRow>
    fun findByProjectIdIn(projectIds: Collection<UUID>): List<MilestoneRow>
}