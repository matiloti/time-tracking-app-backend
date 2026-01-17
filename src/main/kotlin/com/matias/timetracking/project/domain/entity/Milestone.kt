package com.matias.timetracking.project.domain.entity

import com.matias.timetracking.project.domain.exception.DomainException
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@ConsistentCopyVisibility
data class Milestone private constructor(
    val id: UUID? = null,
    val projectId: UUID,
    val name: String,
    val description: String?,
    val startDate: LocalDate?,
    val endDate: LocalDate?,
    val createdAt: LocalDateTime,
    private var updatedAt: LocalDateTime,
) {
    init {
        if(name.isBlank())
            throw DomainException("Milestone name cannot be blank")
        if(endDate != null && startDate != null && endDate.isBefore(startDate))
            throw DomainException("End date cannot be before start date")
    }

    fun getCopy() = this.copy()

    fun updatedAt() = this.updatedAt

    companion object {
        fun create(
            projectId: UUID,
            name: String,
            description: String?,
            startDate: LocalDate?,
            endDate: LocalDate?
        ) = Milestone(
                projectId = projectId,
                name = name,
                description = description,
                startDate = startDate,
                endDate = endDate,
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now()
            )

        fun load(
            id: UUID,
            projectId: UUID,
            name: String,
            description: String?,
            startDate: LocalDate?,
            endDate: LocalDate?,
            createdAt: LocalDateTime,
            updatedAt: LocalDateTime
        ) = Milestone(
                id = id,
                projectId = projectId,
                name = name,
                description = description,
                startDate = startDate,
                endDate = endDate,
                createdAt = createdAt,
                updatedAt = updatedAt
            )
    }
}