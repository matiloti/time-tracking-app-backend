package com.matias.timetracking.project.infrastructure.dao.row

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime
import java.util.*

@Table("milestones")
data class MilestoneRow(
    @Id
    @get:JvmName("getIdValue")
    val id: UUID,
    val projectId: UUID,
    val name: String,
    val description: String?,
    val startDate: LocalDateTime?,
    val endDate: LocalDateTime?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    @Transient
    private val isNewEntity: Boolean? = null
) : Persistable<UUID> {
    override fun getId(): UUID = id
    override fun isNew(): Boolean = isNewEntity ?: false
}