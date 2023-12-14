package my.rest.service.impl;

import my.rest.dto.CoachDto;
import my.rest.model.Coach;
import my.rest.repository.CoachRepository;
import my.rest.service.mapping.CoachMapper;
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
class CoachServiceTest {

    @InjectMocks
    CoachService coachService;

    @Mock
    CoachRepository coachRepository;

    CoachMapper coachMapper = Mappers.getMapper(CoachMapper.class);

    @Test
    void findByIdTest() throws SQLException {
        Coach coach = new Coach("Маргарита", "Мастерова", "86666666666");
        coach.setId(1);

        Mockito.doReturn(Optional.of(coach)).when(coachRepository).findById(1L);

        Assertions.assertEquals(coachMapper.toDTOCoachIgnoreGroupChildClients(coach), coachService.findById(coach.getId()));
    }

    @Test
    void findAllTest() {
        CoachDto coach1 = new CoachDto("Маргарита", "Мастерова", "86666666666");
        coach1.setId(1);

        CoachDto coach2 = new CoachDto("Анна", "Ахматова", "89379120428");
        coach2.setId(2);

        List<CoachDto> coachDtos = new ArrayList<>();
        coachDtos.add(coach1);
        coachDtos.add(coach2);

        List<Coach> coachs = coachDtos.stream().map(coachDto -> coachMapper.dtoToEntity(coachDto)).toList();

        Mockito.when(coachRepository.findAll()).thenReturn(coachs);

        Assertions.assertArrayEquals(coachDtos.toArray(), coachService.findAll().toArray());
    }

    @Test
    void saveTest() {
        CoachDto coach = new CoachDto("Маргарита", "Мастерова", "86666666666");
        coach.setId(1);

        Mockito.when(coachRepository.save(Mockito.any())).thenReturn(coachMapper.dtoToEntity(coach));

        Assertions.assertEquals(coach, coachService.save(coach));
    }

    @Test
    void deleteByIdTest() {
        ArgumentCaptor<Long> valueCapture = ArgumentCaptor.forClass(Long.class);

        Mockito.doNothing().when(coachRepository).deleteById(valueCapture.capture());

        coachService.deleteById(1L);

        Assertions.assertEquals(1L, valueCapture.getValue());
    }
}
