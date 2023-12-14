package my.rest.service.impl;

import my.rest.dto.ClientDto;
import my.rest.model.Client;
import my.rest.repository.ClientRepository;
import my.rest.service.mapping.ClientMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    ClientRepository clientRepository;

    @InjectMocks
    ClientService clientService;

    ClientMapper clientMapper = Mappers.getMapper(ClientMapper.class);

    @Test
    void findByIdTest() throws SQLException {
        Client client = new Client("Маргарита", "Мастерова", "86666666666");
        client.setId(1);

        Mockito.doReturn(Optional.of(client)).when(clientRepository).findById(1L);

        Assertions.assertEquals(clientMapper.toDTOClientIgnoreGroupChildClients(client), clientService.findById(client.getId()));
    }

    @Test
    void findAllTest() {
        ClientDto client1 = new ClientDto("Маргарита", "Мастерова", "86666666666");
        client1.setId(1);

        ClientDto client2 = new ClientDto("Анна", "Ахматова", "89379120428");
        client2.setId(2);

        List<ClientDto> clientDtos = new ArrayList<>();
        clientDtos.add(client1);
        clientDtos.add(client2);

        List<Client> clients = clientDtos.stream().map(clientDto -> clientMapper.dtoToEntity(clientDto)).toList();

        Mockito.when(clientRepository.findAll()).thenReturn(clients);

        Assertions.assertArrayEquals(clientDtos.toArray(), clientService.findAll().toArray());
    }

    @Test
    void saveTest() {
        ClientDto client = new ClientDto("Маргарита", "Мастерова", "86666666666");
        client.setId(1);

        Mockito.when(clientRepository.save(Mockito.any())).thenReturn(clientMapper.dtoToEntity(client));

        Assertions.assertEquals(client, clientService.save(client));
    }

    @Test
    void deleteByIdTest() {
        ArgumentCaptor<Long> valueCapture = ArgumentCaptor.forClass(Long.class);

        Mockito.doNothing().when(clientRepository).deleteById(valueCapture.capture());

        clientService.deleteById(1L);

        Assertions.assertEquals(1L, valueCapture.getValue());
    }
}
