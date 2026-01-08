package com.matias.timetracking.project.infrastructure.configuration

import com.matias.timetracking.project.application.usecase.create.CreateProjectUseCase
import com.matias.timetracking.project.application.usecase.find.FindAllProjectsUseCase
import com.matias.timetracking.project.infrastructure.repository.ProjectRepositoryAdapter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
//@EnableJpaAuditing
open class ProjectConfiguration {
    @Bean
    fun createProjectUseCase(projectRepositoryAdapter: ProjectRepositoryAdapter) =
        CreateProjectUseCase(projectRepositoryAdapter)

    @Bean
    fun findAllProjectsUseCase(projectRepositoryAdapter: ProjectRepositoryAdapter) =
        FindAllProjectsUseCase(projectRepositoryAdapter)
}