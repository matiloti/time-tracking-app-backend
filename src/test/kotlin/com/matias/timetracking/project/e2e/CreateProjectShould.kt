package com.matias.timetracking.project.e2e

import com.matias.timetracking.helper.IntegrationTest
import com.matias.timetracking.project.infrastructure.controller.createproject.dto.CreateProjectRequest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.test.web.servlet.client.RestTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
class CreateProjectShould: IntegrationTest() {

    @LocalServerPort
    var port: Int = 0

    @Autowired
    lateinit var restTemplate: RestTestClient

    @Autowired
    lateinit var jdbc: NamedParameterJdbcTemplate

    @Test
    fun `create project`() {
        restTemplate
            .post()
            .uri("http://localhost:$port/project")
            .body(CreateProjectRequest(name = "Test Project", description = "Test description", categoryId = 1))
            .exchange()
            .expectStatus().isCreated
            .expectHeader().exists("Location")
            .expectHeader().valueMatches("location","/project/.+")

        val rowCount = jdbc.queryForObject("SELECT COUNT(*) FROM projects", emptyMap<String, Any>(), Int::class.java)
        assertEquals(1, rowCount)
    }
}