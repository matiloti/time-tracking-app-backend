package com.matias.timetracking.project.domain.repository

import com.matias.timetracking.project.domain.aggregate.Milestone

interface MilestoneRepository {
    fun save(milestone: Milestone)
}