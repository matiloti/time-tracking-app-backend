package com.matias.timetracking.project.e2e

import com.matias.timetracking.helper.IntegrationTest
import com.matias.timetracking.project.infrastructure.controller.create.dto.CreateProjectRequest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.resttestclient.TestRestTemplate
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate
import org.springframework.boot.resttestclient.postForEntity
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.client.RestTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
class CreateProjectShould: IntegrationTest() {

    @LocalServerPort
    var port: Int = 0

    @Autowired
    lateinit var restTemplate: RestTestClient

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
    }
}