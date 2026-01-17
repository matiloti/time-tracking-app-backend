package com.matias.timetracking.project.infrastructure.controller.listallprojects

import com.matias.timetracking.project.infrastructure.repository.ProjectQueryRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/projects")
class ListAllProjectsController(val projectQueryRepository: ProjectQueryRepository) {
    @GetMapping("/listAll")
    fun listAll(): ResponseEntity<List<ProjectListItemResponse>> =
        projectQueryRepository
            .listAllProjects()
            .mapToResponse()

    private fun List<ProjectListItemResponse>.mapToResponse(): ResponseEntity<List<ProjectListItemResponse>> =
        ResponseEntity.ok(this)
}