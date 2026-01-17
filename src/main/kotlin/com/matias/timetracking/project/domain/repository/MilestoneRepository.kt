package com.matias.timetracking.project.domain.repository

import com.matias.timetracking.project.domain.entity.Milestone

interface MilestoneRepository {
    fun save(milestone: Milestone)
}