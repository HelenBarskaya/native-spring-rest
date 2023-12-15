package my.rest.repository;

import jakarta.annotation.Resource;
import my.rest.model.Coach;
import my.rest.model.Group;
import my.rest.repository.configuration.TestConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(TestConfig.class)
@Transactional
@Testcontainers
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class GroupRepositoryTest {

    @Resource
    private GroupRepository groupRepository;

    @Resource
    private  CoachRepository coachRepository;

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
    void createGroupTest(){
        Coach coach = coachRepository.save(new Coach("Анна", "Ахматова", "89379120428"));
        Group group = groupRepository.save(new Group("Растяжка", coach));

        assertEquals(group, groupRepository.findById(group.getId()).orElseThrow());
    }

    @Test
    void deleteGroupTest(){
        Coach coach = coachRepository.save(new Coach("Анна", "Ахматова", "89379120428"));
        Group group = groupRepository.save(new Group("Растяжка", coach));

        groupRepository.deleteById(group.getId());

        assertNotNull(coachRepository.findById(coach.getId()));
        assertThrows(NoSuchElementException.class,() -> groupRepository.findById(group.getId()).orElseThrow());
    }

    @Test
    void findAllTest(){
        Coach coach = coachRepository.save(new Coach("Анна", "Ахматова", "89379120428"));
        Group group1 = groupRepository.save(new Group("Растяжка", coach));
        Group group2 = groupRepository.save(new Group("Пилатес", coach));

        assertEquals(2, groupRepository.findAll().size());
        assertEquals(group1, groupRepository.findById(group1.getId()).orElseThrow());
        assertEquals(group2, groupRepository.findById(group2.getId()).orElseThrow());
    }
}