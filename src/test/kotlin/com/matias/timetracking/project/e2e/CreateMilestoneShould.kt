package com.matias.timetracking.project.e2e

import com.matias.timetracking.helper.IntegrationTest
import com.matias.timetracking.project.domain.repository.ProjectRepository
import com.matias.timetracking.project.infrastructure.controller.createmilestone.CreateMilestoneRequest
import com.matias.timetracking.project.infrastructure.dao.row.MilestoneRow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.test.web.servlet.client.RestTestClient
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.sql.Timestamp
import java.time.LocalDateTime
import java.util.*
import kotlin.test.Test

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
class CreateMilestoneShould: IntegrationTest() {

    @LocalServerPort
    var port: Int = 0

    @Autowired
    lateinit var jdbc: NamedParameterJdbcTemplate

    @Autowired
    lateinit var restTestClient: RestTestClient

    @Autowired
    lateinit var projectRepository: ProjectRepository

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    fun `create new milestone in project correctly`() {
        // GIVEN
        // already created project
        val projectId = UUID.randomUUID()
        jdbc.update("""
            INSERT INTO projects (
                id,
                name,
                description,
                category_id,
                created_at
            ) VALUES (:id,:name,:description,:categoryId,:createdAt)
        """.trimIndent(),
            mapOf(
                "id" to projectId,
                "name" to "Best Project Ever",
                "description" to "This is the best project ever",
                "categoryId" to 1,
                "createdAt" to Timestamp.valueOf(LocalDateTime.now())
            )
        )

        // WHEN
        // create new milestone in created project
        val request = CreateMilestoneRequest(
            name = "First milestone",
            description = "This milestone must have...",
            startDate = LocalDateTime.now(),
            endDate = LocalDateTime.now().plusDays(7)
        )
        val response = restTestClient
            .post()
            .uri("http://localhost:$port/projects/${projectId}/milestones")
            .body(request)
            .exchange()

        // THEN
        // milestone is correctly created
        response
            .expectStatus().isCreated
            .expectHeader().exists("Location")
            .expectHeader().valueMatches("location","/projects/${projectId}/milestones/.+")

        val milestones = jdbc.query<MilestoneRow>(
            "SELECT * FROM milestones",
            emptyMap<String, Int>()
        ) { rs, _ ->
            MilestoneRow(
                id = UUID.fromString(rs.getString("id")),
                projectId = UUID.fromString(rs.getString("projectId")),
                name = rs.getString("name"),
                description = rs.getString("description"),
                startDate = LocalDateTime.from(rs.getTimestamp("start_date").toInstant()),
                endDate = LocalDateTime.from(rs.getTimestamp("end_date").toInstant()),
                createdAt = LocalDateTime.from(rs.getTimestamp("created_at").toInstant()),
                updatedAt = LocalDateTime.from(rs.getTimestamp("updated_at").toInstant()),
            )
        }
        assertTrue(milestones.isNotEmpty())

        with(milestones.first()) {
            assertEquals(name, request.name)
            assertEquals(description, request.description)
            assertEquals(startDate, request.startDate)
            assertEquals(endDate, request.endDate)
        }
    }
}