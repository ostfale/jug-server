package de.ostfale.jug.server.jdbc

import de.ostfale.jug.server.CreatePersonModel
import de.ostfale.jug.server.repository.PersonRepository
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.http.MediaType
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.web.reactive.server.WebTestClient
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@DisplayName("Test JDBC functions for Person object")
@Testcontainers
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
internal class PersonIT {

    @Autowired
    private lateinit var personRepository: PersonRepository

    @Autowired
    private lateinit var webTestClient: WebTestClient

    companion object {

        @Container
        val container = PostgreSQLContainer<Nothing>("postgres:12").apply {
            withDatabaseName("testdb")
            withUsername("user")
            withPassword("userpw")
        }

        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", container::getJdbcUrl);
            registry.add("spring.datasource.password", container::getPassword);
            registry.add("spring.datasource.username", container::getUsername);
        }
    }

    @Test
    fun `This test should retrieve all persons using REST GET`() {
        // given
        val max = CreatePersonModel.create(aFirstName = "max", anId = null)
        val moritz = CreatePersonModel.create(aFirstName = "moritz", anId = null)
        personRepository.save(max)
        personRepository.save(moritz)
        // then
        webTestClient.get()
            .uri("/api/v1/person/")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody().jsonPath("$.length()").isEqualTo(2)
    }
}