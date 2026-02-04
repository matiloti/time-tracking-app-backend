package com.matias.timetracking.project.infrastructure.controller.createmilestone

import com.matias.timetracking.project.application.usecase.createmilestone.CreateMilestoneCommand
import com.matias.timetracking.project.application.usecase.createmilestone.CreateMilestoneResponse
import com.matias.timetracking.project.application.usecase.createmilestone.CreateMilestoneUseCase
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.util.*

@RestController
@RequestMapping("/projects/{projectId}/milestones")
class CreateMilestoneController(val createMilestoneUseCase: CreateMilestoneUseCase) {

    @PostMapping
    fun createMilestone(
        @PathVariable projectId: UUID,
        @Valid @RequestBody request: CreateMilestoneRequest
    ): ResponseEntity<Any> =
        createMilestoneUseCase
            .execute(request.mapToCommand(projectId))
            .mapToResponse()

    private fun CreateMilestoneRequest.mapToCommand(projectId: UUID): CreateMilestoneCommand =
        CreateMilestoneCommand(
            projectId,
            name,
            description,
            startDate,
            endDate
        )

    private fun CreateMilestoneResponse.mapToResponse(): ResponseEntity<Any> =
        ResponseEntity
            .created(URI.create("/milestones/${id}"))
            .build()
}