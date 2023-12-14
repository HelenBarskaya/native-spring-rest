package my.rest.service.mapping;

import my.rest.dto.ClientDto;
import my.rest.model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Named("ClientMapper")
@Mapper(uses = GroupMapper.class)
public interface ClientMapper {

    @Named("toDTOClientIgnoreGroupChildClients")
    @Mapping(target = "groups", qualifiedByName = {"GroupMapper", "toDTOGroupIgnoreClientsAndCoach"})
    ClientDto toDTOClientIgnoreGroupChildClients(Client client);

    Client dtoToEntity(ClientDto clientDto);
}
