package com.matias.timetracking.project.infrastructure.controller.createproject

import com.matias.timetracking.common.infrastructure.ApiError
import com.matias.timetracking.common.infrastructure.ApiProjectErrorCodes
import com.matias.timetracking.project.application.usecase.createproject.CreateProjectCommand
import com.matias.timetracking.project.application.usecase.createproject.CreateProjectUseCase
import com.matias.timetracking.project.infrastructure.controller.createproject.dto.CreateProjectRequest
import org.springframework.dao.DuplicateKeyException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
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
        val createdId = createProjectUseCase
            .execute(request.mapToCommand())
            .id
        return ResponseEntity.created(URI.create("/project/${createdId}")).build();
    }

    fun CreateProjectRequest.mapToCommand(): CreateProjectCommand =
        CreateProjectCommand(
            name,
            description,
            categoryId
        )

    @ExceptionHandler(DuplicateKeyException::class)
    fun handleDuplicateKeyException(): ResponseEntity<Any> =
        ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(
                ApiError(
                    ApiProjectErrorCodes.DUPLICATED_PROJECT_TITLE.name,
                    ApiProjectErrorCodes.DUPLICATED_PROJECT_TITLE.msg
                )
            )
}