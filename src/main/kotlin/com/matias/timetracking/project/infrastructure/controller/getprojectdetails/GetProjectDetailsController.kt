package com.matias.timetracking.project.infrastructure.controller.getprojectdetails

import com.matias.timetracking.common.infrastructure.ApiProjectErrorCodes
import com.matias.timetracking.project.infrastructure.repository.query.ProjectQueryRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/projects")
class GetProjectDetailsController(private val projectQueryRepository: ProjectQueryRepository) {
    @GetMapping("/{projectId}/details")
    fun getProjectDetails(@PathVariable projectId: UUID): ResponseEntity<Any> =
        projectQueryRepository
            .getProjectDetailsById(projectId)
            .mapToResponse()

    private fun ProjectDetailsDto?.mapToResponse(): ResponseEntity<Any> =
        if (this != null) ResponseEntity.ok(this)
        else ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ApiProjectErrorCodes.PROJECT_ID_NOT_FOUND.getApiError())
}