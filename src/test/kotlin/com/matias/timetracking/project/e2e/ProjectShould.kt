package com.matias.timetracking.project.e2e

import com.matias.timetracking.helper.EndToEndTest
import com.matias.timetracking.project.domain.enums.Priority
import com.matias.timetracking.project.domain.repository.ProjectRepository
import com.matias.timetracking.project.infrastructure.controller.createmilestone.CreateMilestoneRequest
import com.matias.timetracking.project.infrastructure.controller.createproject.CreateProjectRequest
import com.matias.timetracking.project.infrastructure.controller.createtask.CreateTaskRequest
import com.matias.timetracking.project.infrastructure.dao.MilestoneDao
import com.matias.timetracking.project.infrastructure.dao.ProjectDao
import com.matias.timetracking.project.infrastructure.dao.TaskDao
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.web.servlet.client.RestTestClient
import java.time.LocalDate
import java.util.*

class ProjectShould: EndToEndTest() {

    @LocalServerPort
    var port: Int = 0

    @Autowired
    lateinit var restTestClient: RestTestClient

    @Autowired
    lateinit var projectRepository: ProjectRepository

    @Autowired
    lateinit var projectDao: ProjectDao

    @Autowired
    lateinit var milestoneDao: MilestoneDao

    @Autowired
    lateinit var taskDao: TaskDao

    @AfterEach
    fun cleanUp() {
        projectDao.deleteAll()
        milestoneDao.deleteAll()
        taskDao.deleteAll()
    }

    @Test
    fun `create project with milestone and task happy path`() {
        // PROJECT --------------------
        // WHEN - empty DB

        // GIVEN - create new project
        val createProjectRequest = CreateProjectRequest(
            name = "Test Project",
            description = "Test description",
            categoryId = 1
        )
        val projectCreateResponse = restTestClient
            .post()
            .uri("http://localhost:$port/projects")
            .body(createProjectRequest)
            .exchange()

        // THEN - project is correctly created and location is returned
            .expectStatus().isCreated
            .expectHeader().exists("Location")
            .expectHeader().valueMatches("location","/projects/.+")

        val projectLocation = projectCreateResponse
            .returnResult()
            .responseHeaders
            .get("Location")
            ?.first()
        assertNotNull(projectLocation)
        val createdProjectId = UUID.fromString(projectLocation.split("/").last())

        // MILESTONE --------------------
        // WHEN - create new milestone in existing project
        val milestoneRequest = CreateMilestoneRequest(
            name = "First milestone",
            description = "This milestone must have...",
            startDate = LocalDate.now(),
            endDate = LocalDate.now().plusDays(7)
        )
        val milestoneCreateResponse = restTestClient
            .post()
            .uri("http://localhost:$port${projectLocation}/milestones")
            .body(milestoneRequest)
            .exchange()

        // THEN - milestone is correctly created
            .expectStatus().isCreated
            .expectHeader().exists("Location")
            .expectHeader().valueMatches("location","/milestones/.+")

        val milestoneLocation = milestoneCreateResponse
            .returnResult()
            .responseHeaders
            .get("Location")
            ?.first()
        assertNotNull(milestoneLocation)
        val createdMilestoneId = UUID.fromString(milestoneLocation.split("/").last())

        // TASK --------------------
        // WHEN - create new task in existing milestone
        val taskRequest = CreateTaskRequest(
            name = "To check if this test works",
            description = "This test should work pls",
            priorityId = Priority.HIGH.id
        )
        restTestClient
            .post()
            .uri("http://localhost:$port${milestoneLocation}/tasks")
            .body(taskRequest)
            .exchange()

        // THEN - task is correctly created
            .expectStatus().isCreated
            .expectHeader().exists("Location")
            .expectHeader().valueMatches("location","/tasks/.+")

        // ALL --------------------
        // Lastly, check everything is ok
        with(projectRepository.findById(createdProjectId)) {
            assertNotNull(this)
            assertTrue(milestones().isNotEmpty())
            with(milestones().first()) {
                assertEquals(this.projectId, createdProjectId)
                assertEquals(this.name, milestoneRequest.name)
                assertEquals(this.description, milestoneRequest.description)
                assertEquals(this.startDate, milestoneRequest.startDate)
                assertEquals(this.endDate, milestoneRequest.endDate)
                with(this.tasks().first()) {
                    assertEquals(this.milestoneId, createdMilestoneId)
                    assertEquals(this.name, taskRequest.name)
                    assertEquals(this.description, taskRequest.description)
                    assertEquals(this.priority.id, taskRequest.priorityId)
                    assertEquals(this.completed, false)
                }
            }
        }
    }
}