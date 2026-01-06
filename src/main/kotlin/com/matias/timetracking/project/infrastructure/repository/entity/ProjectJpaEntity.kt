package com.matias.timetracking.project.infrastructure.repository.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.time.LocalDateTime
import java.util.*

@Entity
//@EntityListeners(AuditingEntityListener::class)
class ProjectJpaEntity(
    @Id
    var id: UUID?,
    var title: String?,
    var description: String?,
    var categoryId: Int?,
    var createdAt: LocalDateTime?
)