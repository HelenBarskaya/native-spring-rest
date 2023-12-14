package my.rest.controller.impl;

import my.rest.config.AppConfig;
import my.rest.dto.ClientDto;
import my.rest.service.impl.ClientService;
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
class ClientControllerTest {

    private MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();

    private final ClientService clientService = Mockito.mock(ClientService.class);

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new ClientController(clientService)).build();
    }

    @Test
    void findAllClientesTest() throws Exception {
        ClientDto client1 = new ClientDto();
        client1.setId(1);
        client1.setFirstName("Маргарита");
        client1.setLastName("Мастерова");
        client1.setPhoneNumber("86666666666");

        ClientDto client2 = new ClientDto();
        client2.setId(2);
        client2.setFirstName("Анна");
        client2.setLastName("Ахматова");
        client2.setPhoneNumber("89379120428");

        when(clientService.findAll()).thenReturn(List.of(client1, client2));

        mockMvc.perform(get("/clients"))
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

        verify(clientService, times(1)).findAll();
        verifyNoMoreInteractions(clientService);
    }

    @Test
    void findByIdTest() throws Exception {
        ClientDto client = new ClientDto();
        client.setId(1);
        client.setFirstName("Маргарита");
        client.setLastName("Мастерова");
        client.setPhoneNumber("86666666666");

        when(clientService.findById(1L)).thenReturn(client);

        mockMvc.perform(get("/clients/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("firstName", is("Маргарита")))
                .andExpect(jsonPath("lastName", is("Мастерова")))
                .andExpect(jsonPath("phoneNumber", is("86666666666")));

        verify(clientService, times(1)).findById(1L);
        verifyNoMoreInteractions(clientService);
    }

    @Test
    void saveTest() throws Exception {
        ClientDto client = new ClientDto();
        client.setFirstName("Маргарита");
        client.setLastName("Мастерова");
        client.setPhoneNumber("86666666666");

        when(clientService.save(client)).thenReturn(client);

        mockMvc.perform(post("/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(client))
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk());

        verify(clientService, times(1)).save(client);
        verifyNoMoreInteractions(clientService);
    }

    @Test
    void deleteTest() throws Exception {
        mockMvc.perform(delete("/clients/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
