package com.matias.timetracking.project.infrastructure.controller.getmilestonedetails

import com.matias.timetracking.common.infrastructure.ApiProjectErrorCodes
import com.matias.timetracking.project.infrastructure.repository.query.MilestoneQueryRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/milestones")
class GetMilestoneDetailsController(private val milestoneQueryRepository: MilestoneQueryRepository) {
    @GetMapping("/{milestoneId}/details")
    fun getProjectDetails(@PathVariable milestoneId: UUID): ResponseEntity<Any> =
        milestoneQueryRepository
            .getMilestoneDetailsById(milestoneId)
            .mapToResponse()

    private fun MilestoneDetailsDto?.mapToResponse(): ResponseEntity<Any> =
        if (this != null) ResponseEntity.ok(this)
        else ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ApiProjectErrorCodes.MILESTONE_ID_NOT_FOUND.getApiError())
}