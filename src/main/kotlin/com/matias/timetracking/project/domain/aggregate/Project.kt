package com.matias.timetracking.project.domain.aggregate

import com.matias.timetracking.project.domain.entity.Milestone
import com.matias.timetracking.project.domain.exception.DomainException
import java.time.LocalDateTime
import java.util.*

data class Project(
    val id: UUID,
    val name: String,
    val description: String,
    val categoryId: Int,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    private val milestones: MutableList<Milestone>
) {

    fun milestones(): List<Milestone> = milestones.toList()

    fun createNewMilestone(
        name: String,
        description: String?,
        startDate: LocalDateTime?,
        endDate: LocalDateTime?
    ): Milestone {
        if(milestones.any { it.name == name })
            throw DomainException("Milestone with name $name already exists")

        if(milestones.any { it.startDate == startDate })
            throw DomainException("This project already has a milestone with the same start date")

        if(milestones.any { it.endDate == endDate })
            throw DomainException("This project already has a milestone with the same end date")

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
            LocalDateTime.now(),
            mutableListOf()
        )
    }
}