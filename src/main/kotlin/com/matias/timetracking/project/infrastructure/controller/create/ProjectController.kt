package com.matias.timetracking.project.infrastructure.controller.create

import com.matias.timetracking.project.application.usecase.create.ProjectCreationCommand
import com.matias.timetracking.project.application.usecase.create.ProjectCreationUseCase
import com.matias.timetracking.project.infrastructure.controller.create.dto.ProjectCreationRequest
import com.matias.timetracking.project.infrastructure.controller.create.dto.ProjectCreationResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/project")
class ProjectController(val projectCreationUseCase: ProjectCreationUseCase) {

    @PostMapping
    fun createProject(@RequestBody request: ProjectCreationRequest): ResponseEntity<ProjectCreationResponse> {
        val createdId = projectCreationUseCase
            .execute(request.mapToCommand())
            .id
        return ResponseEntity.created(URI.create("/project/${createdId}")).build();
    }

    fun ProjectCreationRequest.mapToCommand(): ProjectCreationCommand =
        ProjectCreationCommand(
            title,
            description,
            categoryId
        )
}