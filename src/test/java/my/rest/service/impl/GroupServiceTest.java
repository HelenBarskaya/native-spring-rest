package my.rest.service.impl;

import my.rest.dto.CoachDto;
import my.rest.dto.GroupDto;
import my.rest.model.Coach;
import my.rest.model.Group;
import my.rest.repository.GroupRepository;
import my.rest.service.mapping.GroupMapper;
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
class GroupServiceTest {

    @InjectMocks
    GroupService groupService;

    @Mock
    GroupRepository groupRepository;

    GroupMapper groupMapper = Mappers.getMapper(GroupMapper.class);

    @Test
    void findByIdTest() throws SQLException {
        Coach coach = new Coach("Маргарита", "Мастерова", "86666666666");
        coach.setId(1);

        Group group = new Group("Растяжка", coach);
        group.setId(1);

        Mockito.doReturn(Optional.of(group)).when(groupRepository).findById(1L);

        Assertions.assertEquals(groupMapper.toDTOGroupIgnoreClientsAndCoach(group), groupService.findById(group.getId()));
    }

    @Test
    void findAllTest() {
        CoachDto coach = new CoachDto("Маргарита", "Мастерова", "86666666666");
        coach.setId(1);

        List<GroupDto> groupDtos = new ArrayList<>();
        groupDtos.add(new GroupDto("Растяжка", coach));
        groupDtos.add(new GroupDto("Пилатес", coach));

        List<Group> coaches = groupDtos.stream().map(groupDto -> groupMapper.dtoToEntity(groupDto)).toList();

        Mockito.when(groupRepository.findAll()).thenReturn(coaches);

        Assertions.assertArrayEquals(groupDtos.toArray(), groupService.findAll().toArray());
    }

    @Test
    void saveTest() {
        CoachDto coach = new CoachDto("Маргарита", "Мастерова", "86666666666");
        coach.setId(1);

        GroupDto group = new GroupDto("Растяжка", coach);
        group.setId(1);

        Mockito.when(groupRepository.save(Mockito.any())).thenReturn(groupMapper.dtoToEntity(group));

        Assertions.assertEquals(group, groupService.save(group));
    }

    @Test
    void deleteByIdTest() {
        ArgumentCaptor<Long> valueCapture = ArgumentCaptor.forClass(Long.class);

        Mockito.doNothing().when(groupRepository).deleteById(valueCapture.capture());

        groupService.deleteById(1L);

        Assertions.assertEquals(1L, valueCapture.getValue());
    }
}
