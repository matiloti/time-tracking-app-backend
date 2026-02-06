package com.matias.timetracking.project.infrastructure.controller.gettaskdetails

import com.matias.timetracking.common.infrastructure.ApiProjectErrorCodes
import com.matias.timetracking.project.infrastructure.repository.query.TaskQueryRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/tasks")
class GetTaskDetailsController(private val taskQueryRepository: TaskQueryRepository) {
    @GetMapping("/{taskId}/details")
    fun getProjectDetails(@PathVariable taskId: UUID): ResponseEntity<Any> = taskQueryRepository
        .getTaskDetailsById(taskId)
        .mapToResponse()

    private fun TaskDetailsDto?.mapToResponse(): ResponseEntity<Any> = if (this != null) {
        ResponseEntity.ok(this)
    } else {
        ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ApiProjectErrorCodes.TASK_ID_NOT_FOUND.getApiError())
    }
}
