package com.matias.timetracking.project.infrastructure.controller.createproject

import com.matias.timetracking.project.application.usecase.createproject.CreateProjectCommand
import com.matias.timetracking.project.application.usecase.createproject.CreateProjectUseCase
import com.matias.timetracking.project.infrastructure.controller.createproject.dto.CreateProjectRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/project")
class CreateProjectController(val createProjectUseCase: CreateProjectUseCase) {

    @PostMapping
    fun createProject(@RequestBody request: CreateProjectRequest): ResponseEntity<Any> {
        try {
            val createdId = createProjectUseCase
                .execute(request.mapToCommand())
                .id
            return ResponseEntity.created(URI.create("/project/${createdId}")).build();
        } catch (e: Exception) {
            print(e)
            return ResponseEntity.internalServerError().build();
        }
    }

    fun CreateProjectRequest.mapToCommand(): CreateProjectCommand =
        CreateProjectCommand(
            name,
            description,
            categoryId
        )
}