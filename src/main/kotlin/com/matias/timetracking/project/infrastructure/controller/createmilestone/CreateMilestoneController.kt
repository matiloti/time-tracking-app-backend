package com.matias.timetracking.project.infrastructure.controller.createmilestone

import com.matias.timetracking.common.infrastructure.ApiError
import com.matias.timetracking.common.infrastructure.ApiProjectErrorCodes
import com.matias.timetracking.project.application.usecase.createmilestone.CreateMilestoneCommand
import com.matias.timetracking.project.application.usecase.createmilestone.CreateMilestoneResponse
import com.matias.timetracking.project.application.usecase.createmilestone.CreateMilestoneUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.util.*

@RestController
@RequestMapping("/projects/{projectId}/milestones")
class CreateMilestoneController(val createMilestoneUseCase: CreateMilestoneUseCase) {

    @PostMapping
    fun createProject(
        @PathVariable projectId: UUID,
        @RequestBody request: CreateMilestoneRequest
    ): ResponseEntity<Any> =
        createMilestoneUseCase
            .execute(request.mapToCommand(projectId))
            .mapToResponse(projectId)

    fun CreateMilestoneRequest.mapToCommand(projectId: UUID): CreateMilestoneCommand =
        CreateMilestoneCommand(
            projectId,
            name,
            description,
            startDate,
            endDate
        )

    fun CreateMilestoneResponse?.mapToResponse(projectId: UUID): ResponseEntity<Any> =
        if (this != null) ResponseEntity
            .created(URI.create("/projects/${projectId}/milestones/${id}"))
            .build()
        else ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(
                ApiError(
                    ApiProjectErrorCodes.PROJECT_ID_DOES_NOT_EXIST.name,
                    ApiProjectErrorCodes.PROJECT_ID_DOES_NOT_EXIST.msg
                )
            )
}