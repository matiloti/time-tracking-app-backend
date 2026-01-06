package com.matias.timetracking.project.infrastructure.repository

import com.matias.timetracking.project.domain.aggregate.Project
import com.matias.timetracking.project.domain.repository.ProjectRepositoryPort
import com.matias.timetracking.project.infrastructure.repository.entity.ProjectJpaEntity
import com.matias.timetracking.project.infrastructure.repository.jpa.ProjectJpaRepository
import org.springframework.stereotype.Repository

@Repository
class ProjectRepositoryAdapter(val projectJpaRepository: ProjectJpaRepository): ProjectRepositoryPort {

    override fun save(project: Project) {
        projectJpaRepository.save(project.createEntityToInsertFromApplication())
    }

    private fun Project.createEntityToInsertFromApplication() =
        ProjectJpaEntity(
            id,
            title,
            description,
            categoryId,
            null
        )

}