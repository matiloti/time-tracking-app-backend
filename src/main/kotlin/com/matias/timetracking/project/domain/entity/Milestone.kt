package com.matias.timetracking.project.domain.entity

import com.matias.timetracking.project.domain.exception.DomainException
import java.time.LocalDateTime
import java.util.*

data class Milestone(
    val id: UUID,
    val projectId: UUID,
    val name: String,
    val description: String?,
    val startDate: LocalDateTime?,
    val endDate: LocalDateTime?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {
    init {
        if(name.isBlank())
            throw DomainException("Milestone name cannot be blank")
        if(endDate != null && startDate != null && endDate.isBefore(startDate))
            throw DomainException("End date cannot be before start date")
    }
}