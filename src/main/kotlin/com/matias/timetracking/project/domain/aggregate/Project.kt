package com.matias.timetracking.project.domain.aggregate

import java.time.LocalDateTime
import java.util.*

class Project(
    val id: UUID,
    val name: String,
    val description: String,
    val categoryId: Int,
    val createdAt: LocalDateTime?,
    private val milestones: MutableList<Milestone>
) {

    fun milestones(): List<Milestone> = milestones.toList()

    fun createNewMilestone(
        name: String,
        description: String?,
        startDate: LocalDateTime?,
        endDate: LocalDateTime?
    ): Milestone {
        val newMilestone = Milestone(
            UUID.randomUUID(),
            id,
            name,
            description,
            startDate,
            endDate,
            LocalDateTime.now(),
            LocalDateTime.now()
        )

        // TODO validate there are not more milestones with the same name, start/end date...

        milestones.add(newMilestone)

        return newMilestone.copy()
    }

    companion object {
        fun createNewProject(name: String, description: String, categoryId: Int) = Project(
            UUID.randomUUID(),
            name,
            description,
            categoryId,
            LocalDateTime.now(),
            mutableListOf()
        )
    }
}