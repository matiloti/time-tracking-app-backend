package com.matias.timetracking.project.domain.entity

import com.matias.timetracking.project.domain.enums.Priority
import com.matias.timetracking.project.domain.exception.DomainException
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@ConsistentCopyVisibility
data class Milestone private constructor(
    val id: UUID,
    val projectId: UUID,
    val name: String,
    val description: String?,
    val startDate: LocalDate?,
    val endDate: LocalDate?,
    val createdAt: LocalDateTime,
    private var updatedAt: LocalDateTime,
    private val tasks: MutableList<Task>,
) {
    init {
        if (name.isBlank()) {
            throw DomainException("Milestone name cannot be blank")
        }
        if (name.length > MAX_NAME_LENGTH) {
            throw DomainException("Milestone name length must be less than $MAX_NAME_LENGTH")
        }
        if (description != null && description.length > MAX_DESCRIPTION_LENGTH) {
            throw DomainException("Milestone description length must be less than $MAX_DESCRIPTION_LENGTH")
        }
        if (endDate != null && startDate != null && endDate.isBefore(startDate)) {
            throw DomainException("Milestone end date cannot be before start date")
        }
        if (updatedAt.isBefore(createdAt)) {
            throw DomainException("Milestone update date cannot be before than creation date")
        }
    }

    fun getCopy() = this.copy()

    fun updatedAt() = this.updatedAt

    fun addTask(name: String, description: String?, priorityId: Int, completed: Boolean): Task {
        if (tasks.any { it.name == name }) {
            throw DomainException("A milestone cannot have two tasks with the same name")
        }

        val newTask = Task.create(
            milestoneId = id,
            name = name,
            description = description,
            priority = Priority.parse(priorityId),
            completed = completed,
        )

        tasks.add(newTask)

        updatedAt = LocalDateTime.now()

        return newTask.getCopy()
    }

    fun tasks() = this.tasks.map { it.getCopy() }

    companion object {
        const val MAX_NAME_LENGTH = 100
        const val MAX_DESCRIPTION_LENGTH = 500

        fun create(
            projectId: UUID,
            name: String,
            description: String?,
            startDate: LocalDate?,
            endDate: LocalDate?,
            tasks: MutableList<Task>,
        ) = Milestone(
            id = UUID.randomUUID(),
            projectId = projectId,
            name = name.trim(),
            description = description?.trim().takeIf { !it.isNullOrBlank() },
            startDate = startDate,
            endDate = endDate,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            tasks = tasks,
        )

        fun load(
            id: UUID,
            projectId: UUID,
            name: String,
            description: String?,
            startDate: LocalDate?,
            endDate: LocalDate?,
            createdAt: LocalDateTime,
            updatedAt: LocalDateTime,
            tasks: MutableList<Task>,
        ) = Milestone(
            id = id,
            projectId = projectId,
            name = name,
            description = description,
            startDate = startDate,
            endDate = endDate,
            createdAt = createdAt,
            updatedAt = updatedAt,
            tasks = tasks,
        )
    }
}
