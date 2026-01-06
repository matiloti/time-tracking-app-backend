package com.matias.timetracking.project.domain.repository

import com.matias.timetracking.project.domain.aggregate.Project

interface ProjectRepositoryPort {
    fun save(project: Project)
}