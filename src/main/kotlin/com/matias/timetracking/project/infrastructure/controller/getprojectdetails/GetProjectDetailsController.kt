package com.matias.timetracking.project.infrastructure.controller.getprojectdetails

import com.matias.timetracking.common.infrastructure.ApiError
import com.matias.timetracking.common.infrastructure.ApiProjectErrorCodes
import com.matias.timetracking.project.infrastructure.repository.ProjectQueryRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/projects")
class GetProjectDetailsController(val projectQueryRepository: ProjectQueryRepository) {
    @GetMapping("/{projectId}")
    fun getProjectDetails(@PathVariable projectId: UUID): ResponseEntity<Any> =
        projectQueryRepository
            .getProjectDetailsById(projectId)
            .mapToResponse()

    fun ProjectDetailsDto?.mapToResponse(): ResponseEntity<Any> =
        if (this != null) ResponseEntity.ok(this)
        else ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(
                ApiError(
                    ApiProjectErrorCodes.PROJECT_ID_DOES_NOT_EXIST.name,
                    ApiProjectErrorCodes.PROJECT_ID_DOES_NOT_EXIST.msg
                )
            )
}