package com.matias.timetracking.project.infrastructure.controller.findproject

import com.matias.timetracking.project.application.usecase.findproject.FindAllProjectsUseCase
import com.matias.timetracking.project.domain.aggregate.Project
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/projects")
class FindProjectController(val findAllProjectsUseCase: FindAllProjectsUseCase) {
    @GetMapping("/findAll")
    fun findAll(): ResponseEntity<List<Project>> {
        try {
            val projects = findAllProjectsUseCase.findAll()
            return ResponseEntity.ok(projects)
        } catch (e: Exception) {
            print(e)
            return ResponseEntity.internalServerError().build();
        }
    }
}