package com.matias.timetracking.project.e2e

import com.matias.timetracking.helper.TestContainersTest
import com.matias.timetracking.project.domain.repository.ProjectRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.test.web.servlet.client.RestTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
class CreateProjectShould: TestContainersTest() {

    @LocalServerPort
    var port: Int = 0

    @Autowired
    lateinit var restTemplate: RestTestClient

    @Autowired
    lateinit var jdbc: NamedParameterJdbcTemplate

    @Autowired
    lateinit var projectRepository: ProjectRepository

//    @Test
//    fun `create new project correctly`() {
//        // GIVEN
//        // empty database
//
//        // WHEN
//        // create new project
//        val request = CreateProjectRequest(
//            name = "Test Project",
//            description = "Test description",
//            categoryId = 1)
//        restTemplate
//            .post()
//            .uri("http://localhost:$port/projects")
//            .body(request)
//            .exchange()
//            .expectStatus().isCreated
//            .expectHeader().exists("Location")
//            .expectHeader().valueMatches("location","/projects/.+")
//
//        // THEN
//        // project is correctly created
//        val projects = projectRepository.findAll()
//        assertTrue(projects.isNotEmpty())
//        with(projects.first()) {
//            assertEquals(request.name, name)
//            assertEquals(request.description, description)
//            assertEquals(request.categoryId, categoryId)
//        }
//    }
}