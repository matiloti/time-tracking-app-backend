package com.matias.timetracking.project.unit

import com.matias.timetracking.project.application.usecase.create.CreateProjectCommand
import com.matias.timetracking.project.application.usecase.create.CreateProjectUseCase
import com.matias.timetracking.project.domain.aggregate.Project
import com.matias.timetracking.project.domain.repository.ProjectRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.doNothing
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExtendWith(MockitoExtension::class)
class CreateProjectUseCaseShould {

    lateinit var useCase: CreateProjectUseCase

    @BeforeEach
    fun setup() {
        val projectRepository: ProjectRepository = mock()
        doNothing().whenever(projectRepository).save(any<Project>())
        useCase = CreateProjectUseCase(projectRepository)
    }

    @Test
    fun `create project`() {
        val result = useCase.execute(
            CreateProjectCommand(name = "test", description = "test", categoryId = 1)
        )
        assertNotNull(result)
    }
}