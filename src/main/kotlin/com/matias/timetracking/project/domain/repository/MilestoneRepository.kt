package com.matias.timetracking.project.domain.repository

import com.matias.timetracking.project.domain.aggregate.Milestone
import java.util.*

interface MilestoneRepository {
    fun save(milestone: Milestone)
    fun findAllByProjectId(projectId: UUID): List<Milestone>
}