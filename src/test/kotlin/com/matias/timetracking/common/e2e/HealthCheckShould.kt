package com.matias.timetracking.common.e2e

import com.matias.timetracking.helper.EndToEndTest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.web.servlet.client.RestTestClient

class HealthCheckShould : EndToEndTest() {

    @LocalServerPort
    var port: Int = 0

    @Autowired
    lateinit var restTestClient: RestTestClient

    @Test
    fun `return UP status in health check endpoint`() {
        restTestClient
            .get()
            .uri("http://localhost:$port/actuator/health")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.status").isEqualTo("UP")
    }
}
