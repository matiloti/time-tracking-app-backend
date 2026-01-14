package com.matias.timetracking.project.infrastructure.controller.getprojectdetails

import com.matias.timetracking.common.infrastructure.ApiError
import com.matias.timetracking.common.infrastructure.ApiProjectErrorCodes
import com.matias.timetracking.project.infrastructure.repository.ProjectQueryRepository
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/projects")
class GetProjectDetailsController(val projectQueryRepository: ProjectQueryRepository) {
    @GetMapping("/{projectId}")
    fun getProjectDetails(@PathVariable projectId: UUID): ResponseEntity<ProjectDetailsDto> {
        val projectDetail = projectQueryRepository.getProjectDetailsById(projectId)
        return ResponseEntity.ok(projectDetail)
    }

    @ExceptionHandler(EmptyResultDataAccessException::class)
    fun handleNotFoundException() : ResponseEntity<Any> =
        ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(
            ApiError(
                ApiProjectErrorCodes.PROJECT_ID_DOES_NOT_EXIST.name,
                ApiProjectErrorCodes.PROJECT_ID_DOES_NOT_EXIST.msg
            )
        )
}