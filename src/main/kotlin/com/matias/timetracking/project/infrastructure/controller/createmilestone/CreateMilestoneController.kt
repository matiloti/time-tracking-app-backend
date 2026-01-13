package com.matias.timetracking.project.infrastructure.controller.createmilestone

import com.matias.timetracking.project.application.usecase.createmilestone.CreateMilestoneCommand
import com.matias.timetracking.project.application.usecase.createmilestone.CreateMilestoneUseCase
import com.matias.timetracking.project.infrastructure.controller.createmilestone.dto.CreateMilestoneRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import java.util.UUID

@RestController
@RequestMapping("/projects/{projectId}/milestone")
class CreateMilestoneController(val createMilestoneUseCase: CreateMilestoneUseCase) {

    @PostMapping
    fun createProject(@PathVariable projectId: UUID, @RequestBody request: CreateMilestoneRequest): ResponseEntity<Any> {
        try {
            val createdId = createMilestoneUseCase
                .execute(request.mapToCommand(projectId))
                .id
            return ResponseEntity.created(URI.create("/milestone/${createdId}")).build();
        } catch (e: Exception) {
            print(e)
            return ResponseEntity.internalServerError().build();
        }
    }

    fun CreateMilestoneRequest.mapToCommand(projectId: UUID): CreateMilestoneCommand =
        CreateMilestoneCommand(
            projectId,
            name,
            description,
            startDate,
            endDate
        )
}