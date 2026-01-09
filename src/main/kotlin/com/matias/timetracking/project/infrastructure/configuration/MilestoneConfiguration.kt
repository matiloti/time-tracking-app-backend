package com.matias.timetracking.project.infrastructure.configuration

import com.matias.timetracking.project.application.usecase.createmilestone.CreateMilestoneUseCase
import com.matias.timetracking.project.application.usecase.createproject.CreateProjectUseCase
import com.matias.timetracking.project.application.usecase.findmilestone.FindProjectMilestonesUseCase
import com.matias.timetracking.project.application.usecase.findproject.FindAllProjectsUseCase
import com.matias.timetracking.project.infrastructure.repository.MilestoneRepositoryAdapter
import com.matias.timetracking.project.infrastructure.repository.ProjectRepositoryAdapter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
//@EnableJpaAuditing
open class MilestoneConfiguration {
    @Bean
    fun createMilestoneUseCase(milestoneRepositoryAdapter: MilestoneRepositoryAdapter) =
        CreateMilestoneUseCase(milestoneRepositoryAdapter)

    @Bean
    fun findMilestoneUseCase(milestoneRepositoryAdapter: MilestoneRepositoryAdapter) =
        FindProjectMilestonesUseCase(milestoneRepositoryAdapter)
}