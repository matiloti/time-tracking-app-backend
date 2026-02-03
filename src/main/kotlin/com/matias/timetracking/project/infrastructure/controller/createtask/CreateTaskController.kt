package com.matias.timetracking.project.infrastructure.controller.createtask

import com.matias.timetracking.project.application.usecase.createtask.CreateTaskCommand
import com.matias.timetracking.project.application.usecase.createtask.CreateTaskResponse
import com.matias.timetracking.project.application.usecase.createtask.CreateTaskUseCase
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.util.*

@RestController
@RequestMapping("/milestones/{milestoneId}/tasks")
class CreateTaskController(private val createTaskUseCase: CreateTaskUseCase) {

    @PostMapping
    fun createTask(
        @PathVariable milestoneId: UUID,
        @Valid @RequestBody request: CreateTaskRequest
    ): ResponseEntity<Any> =
        createTaskUseCase
            .execute(request.mapToCommand(milestoneId))
            .mapToResponse()

    private fun CreateTaskRequest.mapToCommand(milestoneId: UUID): CreateTaskCommand =
        CreateTaskCommand(
            name = name,
            description = description,
            priority = priorityId,
            milestoneId = milestoneId
        )

    private fun CreateTaskResponse.mapToResponse(): ResponseEntity<Any> =
        ResponseEntity
            .created(URI.create("/tasks/${id}"))
            .build()
}