package com.matias.timetracking.project.domain.repository

import com.matias.timetracking.project.domain.aggregate.Project

interface ProjectRepository {
    fun save(project: Project)
    fun findAll(): List<Project>
}