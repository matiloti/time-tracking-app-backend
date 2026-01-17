//package com.matias.timetracking.project.e2e
//
//import com.matias.timetracking.helper.TestContainersTest
//import com.matias.timetracking.project.domain.repository.ProjectRepository
//import com.matias.timetracking.project.infrastructure.dao.ProjectDao
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient
//import org.springframework.boot.test.context.SpringBootTest
//import org.springframework.boot.test.web.server.LocalServerPort
//import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
//import org.springframework.test.web.servlet.client.RestTestClient
//import org.springframework.transaction.annotation.Propagation
//import org.springframework.transaction.annotation.Transactional
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureRestTestClient
//@Transactional(propagation = Propagation.NOT_SUPPORTED)
//class CreateMilestoneShould: TestContainersTest() {
//
//    @LocalServerPort
//    var port: Int = 0
//
//    @Autowired
//    lateinit var jdbc: NamedParameterJdbcTemplate
//
//    @Autowired
//    lateinit var restTestClient: RestTestClient
//
//    @Autowired
//    lateinit var projectRepository: ProjectRepository
//
//    @Autowired
//    lateinit var projectDao: ProjectDao
//
////    @Test
////    @Rollback
////    fun `create new milestone in project correctly`() {
////        // GIVEN
////        // already created project
////        val projectRow = ProjectRow(
////            UUID.randomUUID(),
////            "Best project ever",
////            "This is the best project ever",
////            1,
////            LocalDateTime.now()
////            )
////        projectDao.save(projectRow)
////
////        // WHEN
////        // create new milestone in created project
////        val request = CreateMilestoneRequest(
////            name = "First milestone",
////            description = "This milestone must have...",
////            startDate = LocalDateTime.now(),
////            endDate = LocalDateTime.now().plusDays(7)
////        )
////        val response = restTestClient
////            .post()
////            .uri("http://localhost:$port/projects/${projectRow.id}/milestones")
////            .body(request)
////            .exchange()
////
////        // THEN
////        // milestone is correctly created
////        response
////            .expectStatus().isCreated
////            .expectHeader().exists("Location")
////            .expectHeader().valueMatches("location","/projects/${projectRow.id}/milestones/.+")
////
////        val project = projectRepository.findById(projectRow.id)
////        assertNotNull(project)
////        assertTrue(project!!.milestones().isNotEmpty())
////
////        with(project.milestones().first()) {
////            assertEquals(name, request.name)
////            assertEquals(description, request.description)
////            assertEquals(startDate?.truncatedTo(ChronoUnit.MILLIS), request.startDate?.truncatedTo(ChronoUnit.MILLIS))
////            assertEquals(endDate?.truncatedTo(ChronoUnit.MILLIS), request.endDate?.truncatedTo(ChronoUnit.MILLIS))
////        }
////    }
//}