package my.rest.repository;

import jakarta.annotation.Resource;
import my.rest.model.Coach;
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
class CoachRepositoryTest {

    @Resource
    private CoachRepository coachRepository;

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
        Coach coach = coachRepository.save(
                new Coach("Анна", "Ахматова", "89379120428")
        );
        assertEquals(coach, coachRepository.findById(coach.getId()).orElseThrow());
    }

    @Test
    void deleteCoachTest() {
        Coach coach = coachRepository.save(
                new Coach("Анна", "Ахматова", "89379120428")
        );
        coachRepository.deleteById(coach.getId());
        assertThrows(NoSuchElementException.class, () -> coachRepository.findById(coach.getId()).orElseThrow());
    }

    @Test
    void findAllTest() {
        Coach coach1 = coachRepository.save(
                new Coach("Маргарита", "Мастерова", "86666666666")
        );
        Coach coach2 = coachRepository.save(
                new Coach("Анна", "Ахматова", "89379120428")
        );

        assertEquals(2, coachRepository.findAll().size());
        assertEquals(coach1, coachRepository.findById(coach1.getId()).orElseThrow());
        assertEquals(coach2, coachRepository.findById(coach2.getId()).orElseThrow());
    }


}