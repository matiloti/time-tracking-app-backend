package com.matias.timetracking.project.infrastructure.controller.findmilestone

import com.matias.timetracking.project.application.usecase.findmilestone.FindProjectMilestonesCommand
import com.matias.timetracking.project.application.usecase.findmilestone.FindProjectMilestonesUseCase
import com.matias.timetracking.project.domain.aggregate.Milestone
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/projects/{projectId}")
class FindProjectMilestonesController(val findProjectMilestonesUseCase: FindProjectMilestonesUseCase) {

    @GetMapping("/milestones")
    fun findAll(@PathVariable projectId: UUID): ResponseEntity<List<Milestone>> {
        try {
            val projects = findProjectMilestonesUseCase.execute(FindProjectMilestonesCommand(projectId))
            return ResponseEntity.ok(projects)
        } catch (e: Exception) {
            print(e)
            return ResponseEntity.internalServerError().build();
        }
    }
}