package com.matias.timetracking.project.infrastructure.repository.jpa

import com.matias.timetracking.project.infrastructure.repository.entity.ProjectJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ProjectJpaRepository: JpaRepository<ProjectJpaEntity, UUID>