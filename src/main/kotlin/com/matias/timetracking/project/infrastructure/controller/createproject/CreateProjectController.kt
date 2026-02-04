package com.matias.timetracking.project.infrastructure.controller.createproject

import com.matias.timetracking.project.application.usecase.createproject.CreateProjectCommand
import com.matias.timetracking.project.application.usecase.createproject.CreateProjectResponse
import com.matias.timetracking.project.application.usecase.createproject.CreateProjectUseCase
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/projects")
class CreateProjectController(private val createProjectUseCase: CreateProjectUseCase) {

    @PostMapping
    fun createProject(@Valid @RequestBody request: CreateProjectRequest): ResponseEntity<Any> =
        createProjectUseCase
            .execute(request.mapToCommand())
            .mapToResponse()

    private fun CreateProjectRequest.mapToCommand(): CreateProjectCommand =
        CreateProjectCommand(
            name,
            description,
            categoryId
        )

    private fun CreateProjectResponse.mapToResponse(): ResponseEntity<Any> =
        ResponseEntity.created(URI.create("/projects/${id}")).build()
}