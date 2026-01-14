package com.matias.timetracking.project.e2e

import com.matias.timetracking.helper.IntegrationTest
import com.matias.timetracking.project.domain.repository.ProjectRepository
import com.matias.timetracking.project.infrastructure.controller.createproject.CreateProjectRequest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.test.web.servlet.client.RestTestClient
import org.springframework.transaction.annotation.Transactional

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
class CreateProjectShould: IntegrationTest() {

    @LocalServerPort
    var port: Int = 0

    @Autowired
    lateinit var restTemplate: RestTestClient

    @Autowired
    lateinit var jdbc: NamedParameterJdbcTemplate

    @Autowired
    lateinit var projectRepository: ProjectRepository

    @Test
    @Transactional
    fun `create new project correctly`() {
        // GIVEN
        // empty database

        // WHEN
        // create new project
        val request = CreateProjectRequest(
            name = "Test Project",
            description = "Test description",
            categoryId = 1)
        restTemplate
            .post()
            .uri("http://localhost:$port/projects")
            .body(request)
            .exchange()
            .expectStatus().isCreated
            .expectHeader().exists("Location")
            .expectHeader().valueMatches("location","/projects/.+")

        // THEN
        // project is correctly created
        val projects = projectRepository.findAll()
        assertTrue(projects.isNotEmpty())
        with(projects.first()) {
            assertEquals(request.name, name)
            assertEquals(request.description, description)
            assertEquals(request.categoryId, categoryId)
        }
    }
}