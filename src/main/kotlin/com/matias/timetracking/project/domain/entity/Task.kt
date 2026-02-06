package com.matias.timetracking.project.domain.entity

import com.matias.timetracking.project.domain.enums.Priority
import com.matias.timetracking.project.domain.exception.DomainException
import java.time.LocalDateTime
import java.util.*

@ConsistentCopyVisibility
data class Task private constructor(
    private var id: UUID? = null,
    val milestoneId: UUID,
    val name: String,
    val description: String? = null,
    val priority: Priority,
    val completed: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {
    init {
        if (name.isBlank()) {
            throw DomainException("Task name cannot be blank")
        }
        if (name.length > MAX_NAME_LENGTH) {
            throw DomainException("Task '$name' name length must be less than $MAX_NAME_LENGTH")
        }
        if (description != null && description.length > MAX_DESCRIPTION_LENGTH) {
            throw DomainException("Task '$name' description length must be less than $MAX_DESCRIPTION_LENGTH")
        }
        if (priority.isInvalid()) {
            throw DomainException("Task '$name' with invalid priority value")
        }
        if (updatedAt.isBefore(createdAt)) {
            throw DomainException("Task '$name' update date cannot be before than creation date")
        }
    }

    fun id(): UUID? = id?.let { UUID.fromString(it.toString()) }

    fun getCopy() = this.copy()

    companion object {
        const val MAX_NAME_LENGTH = 100
        const val MAX_DESCRIPTION_LENGTH = 500

        fun create(milestoneId: UUID, name: String, description: String?, priority: Priority, completed: Boolean) =
            Task(
                milestoneId = milestoneId,
                name = name.trim(),
                description = description?.trim().takeIf { !it.isNullOrBlank() },
                priority = priority,
                completed = completed,
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now(),
            )

        fun load(
            id: UUID,
            milestoneId: UUID,
            name: String,
            description: String?,
            priority: Priority,
            createdAt: LocalDateTime,
            updatedAt: LocalDateTime,
            completed: Boolean,
        ) = Task(
            id = id,
            milestoneId = milestoneId,
            name = name.trim(),
            description = description?.trim().takeIf { !it.isNullOrBlank() },
            priority = priority,
            completed = completed,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )

        fun load(
            id: UUID,
            milestoneId: UUID,
            name: String,
            description: String?,
            priorityValue: Int,
            createdAt: LocalDateTime,
            updatedAt: LocalDateTime,
            completed: Boolean,
        ) = Task(
            id = id,
            milestoneId = milestoneId,
            name = name.trim(),
            description = description?.trim().takeIf { !it.isNullOrBlank() },
            priority = Priority.parse(priorityValue),
            completed = completed,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )
    }
}
