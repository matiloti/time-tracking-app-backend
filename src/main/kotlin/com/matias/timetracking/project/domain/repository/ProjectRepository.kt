package com.matias.timetracking.project.domain.repository

import com.matias.timetracking.project.domain.aggregate.Project
import java.util.*

interface ProjectRepository {
    fun save(project: Project): Project
    fun findById(projectId: UUID): Project?
    fun findByMilestoneId(milestoneId: UUID): Project?
}