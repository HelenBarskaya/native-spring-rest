package my.rest.service.mapping;

import my.rest.dto.GroupDto;
import my.rest.model.Group;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;


@Named("GroupMapper")
@Mapper(uses = CoachMapper.class)
public interface GroupMapper {

    @Named("toDTOGroupIgnoreClientsAndCoach")
    @Mapping(target = "clients", ignore = true)
    @Mapping(target = "coach", ignore = true)
    GroupDto toDTOGroupIgnoreClientsAndCoach(Group group);

    Group dtoToEntity(GroupDto groupDto);
}
