package my.rest.service.impl;

import my.rest.dto.ClientDto;
import my.rest.model.Client;
import my.rest.repository.ClientRepository;
import my.rest.service.SimpleService;
import my.rest.service.mapping.ClientMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
public class ClientService implements SimpleService<ClientDto> {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper = Mappers.getMapper(ClientMapper.class);

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public ClientDto findById(Long id) throws SQLException {
        return clientMapper.toDTOClientIgnoreGroupChildClients(
                clientRepository.findById(id).orElseThrow(SQLException::new)
        );
    }

    @Transactional(readOnly = true)
    @Override
    public List<ClientDto> findAll() {
        List<Client> clients = clientRepository.findAll();
        return clients.stream().map(clientMapper::toDTOClientIgnoreGroupChildClients).toList();
    }

    @Transactional
    @Override
    public ClientDto save(ClientDto entity) {
        return clientMapper.toDTOClientIgnoreGroupChildClients(
                clientRepository.save(clientMapper.dtoToEntity(entity))
        );
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        clientRepository.deleteById(id);
    }
}
