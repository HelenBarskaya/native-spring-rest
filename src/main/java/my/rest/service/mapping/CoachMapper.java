package my.rest.service.mapping;

import my.rest.dto.CoachDto;
import my.rest.model.Coach;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Named("CoachMapper")
@Mapper(uses = GroupMapper.class)
public interface CoachMapper {

    @Named("toDTOCoachIgnoreGroupChildClients")
    @Mapping(target = "groups", qualifiedByName = {"GroupMapper", "toDTOGroupIgnoreClientsAndCoach"})
    CoachDto toDTOCoachIgnoreGroupChildClients(Coach coach);

    @Mapping(target = "id", ignore = true)
    Coach dtoToEntity(CoachDto coachDto);
}
