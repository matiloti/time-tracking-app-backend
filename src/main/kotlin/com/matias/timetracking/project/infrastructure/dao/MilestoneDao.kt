package com.matias.timetracking.project.infrastructure.dao

import com.matias.timetracking.project.infrastructure.dao.row.MilestoneRow
import org.springframework.data.repository.CrudRepository
import java.util.*

interface MilestoneDao: CrudRepository<MilestoneRow, UUID> {
    fun findByProjectId(projectId: UUID): List<MilestoneRow>
}