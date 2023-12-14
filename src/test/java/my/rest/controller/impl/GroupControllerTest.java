package my.rest.controller.impl;

import my.rest.config.AppConfig;
import my.rest.dto.CoachDto;
import my.rest.dto.GroupDto;
import my.rest.service.impl.GroupService;
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
class GroupControllerTest {

    private MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();

    private final GroupService groupService = Mockito.mock(GroupService.class);

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new GroupController(groupService)).build();
    }

    @Test
    void findAllGroupsTest() throws Exception {
        CoachDto coach = new CoachDto();
        coach.setId(1);
        coach.setFirstName("Маргарита");
        coach.setLastName("Мастерова");
        coach.setPhoneNumber("86666666666");

        GroupDto group = new GroupDto();
        group.setId(1);
        group.setName("Джиу-джитсу");
        group.setCoach(coach);

        when(groupService.findAll()).thenReturn(List.of(group));

        mockMvc.perform(get("/groups"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Джиу-джитсу")));

        verify(groupService, times(1)).findAll();
        verifyNoMoreInteractions(groupService);
    }

    @Test
    void findByIdTest() throws Exception {
        CoachDto coach = new CoachDto();
        coach.setId(1);
        coach.setFirstName("Маргарита");
        coach.setLastName("Мастерова");
        coach.setPhoneNumber("86666666666");

        GroupDto group = new GroupDto();
        group.setId(1);
        group.setName("Джиу-джитсу");
        group.setCoach(coach);


        when(groupService.findById(1L)).thenReturn(group);

        mockMvc.perform(get("/groups/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("name", is("Джиу-джитсу")));

        verify(groupService, times(1)).findById(1L);
        verifyNoMoreInteractions(groupService);
    }

    @Test
    void saveTest() throws Exception {
        CoachDto coach = new CoachDto();
        coach.setId(1);
        coach.setFirstName("Маргарита");
        coach.setLastName("Мастерова");
        coach.setPhoneNumber("86666666666");

        GroupDto group = new GroupDto();
        group.setId(1);
        group.setName("Джиу-джитсу");
        group.setCoach(coach);

        when(groupService.save(group)).thenReturn(group);

        mockMvc.perform(post("/groups")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(group))
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk());

        verify(groupService, times(1)).save(group);
        verifyNoMoreInteractions(groupService);
    }

    @Test
    void deleteTest() throws Exception {
        mockMvc.perform(delete("/groups/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
