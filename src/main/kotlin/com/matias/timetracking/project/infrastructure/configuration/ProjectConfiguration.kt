package com.matias.timetracking.project.infrastructure.configuration

import com.matias.timetracking.project.application.usecase.create.ProjectCreationUseCase
import com.matias.timetracking.project.infrastructure.repository.ProjectRepositoryAdapter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@Configuration
//@EnableJpaAuditing
open class ProjectConfiguration {
    @Bean
    open fun projectCreationUseCase(projectRepositoryAdapter: ProjectRepositoryAdapter) =
        ProjectCreationUseCase(projectRepositoryAdapter)
}