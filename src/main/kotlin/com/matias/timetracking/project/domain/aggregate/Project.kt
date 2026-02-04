package com.matias.timetracking.project.domain.aggregate

import com.matias.timetracking.project.domain.entity.Milestone
import com.matias.timetracking.project.domain.entity.Task
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

    init {
        if(name.isBlank())
            throw DomainException("Project name cannot be blank")
        if(name.length > MAX_NAME_LENGTH)
            throw DomainException("Project name length must be less than $MAX_NAME_LENGTH")
        if(description != null && description.length > MAX_DESCRIPTION_LENGTH)
            throw DomainException("Project description length must be less than $MAX_DESCRIPTION_LENGTH")
        if(categoryId < 0)
            throw DomainException("Project category id must be greater than 0")
        if(updatedAt.isBefore(createdAt))
            throw DomainException("Project update date cannot be before than creation date")
    }

    fun milestones(): List<Milestone> = milestones.toList()

    fun milestoneTasks(milestoneId: UUID): List<Task>? = milestones.find { it.id == milestoneId }?.tasks()

    fun allMilestoneTasks() = milestones.flatMap { it.tasks() }

    fun updatedAt() = this.updatedAt

    fun addMilestone(
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
            endDate = endDate,
            mutableListOf()
        )

        milestones.add(newMilestone)

        updatedAt = LocalDateTime.now()

        return newMilestone.getCopy()
    }

    fun addTaskToMilestone(
        name: String,
        description: String?,
        priorityId: Int,
        milestoneId: UUID,
    ) : Task {
        val milestone = milestones.find { it.id == milestoneId }
        if(milestone == null)
            throw DomainException("This milestone is not contained in this project")

        val newTask = milestone.addTask(
            name = name,
            description = description,
            priorityId = priorityId,
            false
        )

        updatedAt = LocalDateTime.now()

        return newTask
    }

    companion object {
        const val MAX_NAME_LENGTH = 100
        const val MAX_DESCRIPTION_LENGTH = 500

        fun create(
            name: String,
            description: String?,
            categoryId: Int
        ) = Project(
                name = name.trim(),
                description = description?.trim().takeIf { !it.isNullOrBlank() },
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