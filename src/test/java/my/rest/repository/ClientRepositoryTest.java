package my.rest.repository;

import jakarta.annotation.Resource;
import my.rest.model.Client;
import my.rest.repository.configuration.TestConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringJUnitConfig(TestConfig.class)
@Transactional
@Testcontainers
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class ClientRepositoryTest {

    @Resource
    private ClientRepository clientRepository;

    @Container
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(DockerImageName.parse("postgres")
            .withTag("15.1"))
            .withDatabaseName("postgres-test-db")
            .withUsername("test")
            .withPassword("password")
            .withInitScript("scripts/init-test-database.sql");


    @BeforeAll
    static void beforeAll() {
        postgreSQLContainer.start();

        System.setProperty("db.url", postgreSQLContainer.getJdbcUrl());
    }

    @AfterAll
    static void afterAll() {
        postgreSQLContainer.stop();
    }

    @Test
    void findByIdTest() {
        Client client = clientRepository.save(
                new Client("Анна", "Ахматова", "89379120428")
        );
        assertEquals(client, clientRepository.findById(client.getId()).orElseThrow());
    }

    @Test
    void deleteClientTest() {
        Client client = clientRepository.save(
                new Client("Анна", "Ахматова", "89379120428")
        );
        clientRepository.deleteById(client.getId());
        assertThrows(NoSuchElementException.class, () -> clientRepository.findById(client.getId()).orElseThrow());
    }

    @Test
    void findAllTest() {
        Client client1 = clientRepository.save(
                new Client("Маргарита", "Мастерова", "86666666666")
        );
        Client client2 = clientRepository.save(
                new Client("Анна", "Ахматова", "89379120428")
        );

        assertEquals(2, clientRepository.findAll().size());
        assertEquals(client1, clientRepository.findById(client1.getId()).orElseThrow());
        assertEquals(client2, clientRepository.findById(client2.getId()).orElseThrow());
    }


}