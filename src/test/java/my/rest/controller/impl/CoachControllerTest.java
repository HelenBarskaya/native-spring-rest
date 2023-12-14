package my.rest.controller.impl;

import my.rest.config.AppConfig;
import my.rest.dto.CoachDto;
import my.rest.service.impl.CoachService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ContextConfiguration(classes = {AppConfig.class})
class CoachControllerTest {

    private MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();

    private final CoachService coachService = Mockito.mock(CoachService.class);

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new CoachController(coachService)).build();
    }

    @Test
    void findAllCoachesTest() throws Exception {
        CoachDto coach1 = new CoachDto();
        coach1.setId(1);
        coach1.setFirstName("Маргарита");
        coach1.setLastName("Мастерова");
        coach1.setPhoneNumber("86666666666");

        CoachDto coach2 = new CoachDto();
        coach2.setId(2);
        coach2.setFirstName("Анна");
        coach2.setLastName("Ахматова");
        coach2.setPhoneNumber("89379120428");

        when(coachService.findAll()).thenReturn(List.of(coach1, coach2));

        mockMvc.perform(get("/coaches"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].firstName", is("Маргарита")))
                .andExpect(jsonPath("$[0].lastName", is("Мастерова")))
                .andExpect(jsonPath("$[0].phoneNumber", is("86666666666")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].firstName", is("Анна")))
                .andExpect(jsonPath("$[1].lastName", is("Ахматова")))
                .andExpect(jsonPath("$[1].phoneNumber", is("89379120428")));

        verify(coachService, times(1)).findAll();
        verifyNoMoreInteractions(coachService);
    }

    @Test
    void findByIdTest() throws Exception {
        CoachDto coach = new CoachDto();
        coach.setId(1);
        coach.setFirstName("Маргарита");
        coach.setLastName("Мастерова");
        coach.setPhoneNumber("86666666666");

        when(coachService.findById(1L)).thenReturn(coach);

        mockMvc.perform(get("/coaches/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("firstName", is("Маргарита")))
                .andExpect(jsonPath("lastName", is("Мастерова")))
                .andExpect(jsonPath("phoneNumber", is("86666666666")));

        verify(coachService, times(1)).findById(1L);
        verifyNoMoreInteractions(coachService);
    }

    @Test
    void saveTest() throws Exception {
        CoachDto coach = new CoachDto();
        coach.setFirstName("Маргарита");
        coach.setLastName("Мастерова");
        coach.setPhoneNumber("86666666666");

        when(coachService.save(coach)).thenReturn(coach);

        mockMvc.perform(post("/coaches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(coach))
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk());

        verify(coachService, times(1)).save(coach);
        verifyNoMoreInteractions(coachService);
    }

    @Test
    void deleteTest() throws Exception {
        mockMvc.perform(delete("/coaches/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
