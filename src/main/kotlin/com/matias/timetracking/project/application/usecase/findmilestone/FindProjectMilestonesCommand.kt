package com.matias.timetracking.project.application.usecase.findmilestone

import java.util.UUID

data class FindProjectMilestonesCommand(
    val projectId: UUID
)