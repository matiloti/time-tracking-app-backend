package com.matias.timetracking.project.domain.aggregate

import com.matias.timetracking.project.domain.entity.Milestone
import com.matias.timetracking.project.domain.exception.DomainException
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@ConsistentCopyVisibility
data class Project private constructor(
    val id: UUID? = null,
    val name: String,
    val description: String? = null,
    val categoryId: Int,
    val createdAt: LocalDateTime,
    private var updatedAt: LocalDateTime,
    private val milestones: MutableList<Milestone>
) {

    fun milestones(): List<Milestone> = milestones.toList()

    fun updatedAt() = this.updatedAt

    fun createNewMilestone(
        name: String,
        description: String?,
        startDate: LocalDate?,
        endDate: LocalDate?
    ): Milestone {
        if(id == null)
            throw IllegalStateException("Project id cannot be null")

        if(milestones.any { it.name == name })
            throw DomainException("Milestone with name $name already exists")

        if(milestones.mapNotNull { it.startDate }.any { it == startDate })
            throw DomainException("This project already has a milestone with the same start date")

        if(milestones.mapNotNull { it.endDate }.any { it == endDate })
            throw DomainException("This project already has a milestone with the same end date")

        val newMilestone = Milestone.create(
            projectId = id,
            name = name,
            description = description,
            startDate = startDate,
            endDate = endDate
        )

        milestones.add(newMilestone)

        updatedAt = LocalDateTime.now()

        return newMilestone.getCopy()
    }

    companion object {
        fun create(
            name: String,
            description: String?,
            categoryId: Int
        ) = Project(
                name = name,
                description = description,
                categoryId = categoryId,
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now(),
                milestones = mutableListOf()
            )

        fun load(
            id: UUID, name: String,
            description: String?,
            categoryId: Int,
            createdAt: LocalDateTime,
            updatedAt: LocalDateTime,
            milestones: MutableList<Milestone>
        ) = Project(
                id = id,
                name = name,
                description = description,
                categoryId = categoryId,
                createdAt = createdAt,
                updatedAt = updatedAt,
                milestones = milestones
            )
    }
}