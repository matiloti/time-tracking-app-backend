package com.matias.timetracking.project.e2e

import com.matias.timetracking.helper.EndToEndTest
import com.matias.timetracking.project.domain.repository.ProjectRepository
import com.matias.timetracking.project.infrastructure.controller.createmilestone.CreateMilestoneRequest
import com.matias.timetracking.project.infrastructure.controller.createproject.CreateProjectRequest
import com.matias.timetracking.project.infrastructure.dao.MilestoneDao
import com.matias.timetracking.project.infrastructure.dao.ProjectDao
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

    @AfterEach
    fun cleanUp() {
        projectDao.deleteAll()
        milestoneDao.deleteAll()
    }

    @Test
    fun `create new project and add new milestone happy path`() {
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

        // WHEN - create new milestone in existing project
        val request = CreateMilestoneRequest(
            name = "First milestone",
            description = "This milestone must have...",
            startDate = LocalDate.now(),
            endDate = LocalDate.now().plusDays(7)
        )
        restTestClient
            .post()
            .uri("http://localhost:$port${projectLocation}/milestones")
            .body(request)
            .exchange()

        // THEN - milestone is correctly created
            .expectStatus().isCreated
            .expectHeader().exists("Location")
            .expectHeader().valueMatches("location","${projectLocation}/milestones/.+")

        val projectId = UUID.fromString(projectLocation.split("/").last())
        with(projectRepository.findById(projectId)) {
            assertNotNull(this)
            assertTrue(milestones().isNotEmpty())
            with(milestones().first()) {
                assertEquals(name, request.name)
                assertEquals(description, request.description)
                assertEquals(startDate, request.startDate)
                assertEquals(endDate, request.endDate)
            }
        }
    }
}