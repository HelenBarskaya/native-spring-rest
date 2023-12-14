package my.rest.service.impl;

import my.rest.dto.CoachDto;
import my.rest.model.Coach;
import my.rest.repository.CoachRepository;
import my.rest.service.SimpleService;
import my.rest.service.mapping.CoachMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
public class CoachService implements SimpleService<CoachDto> {

    private final CoachRepository coachRepository;
    private final CoachMapper coachMapper = Mappers.getMapper(CoachMapper.class);

    public CoachService(CoachRepository coachRepository) {
        this.coachRepository = coachRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public CoachDto findById(Long id) throws SQLException {
        return coachMapper.toDTOCoachIgnoreGroupChildClients(
                coachRepository.findById(id).orElseThrow(SQLException::new)
        );
    }

    @Transactional(readOnly = true)
    @Override
    public List<CoachDto> findAll() {
        List<Coach> coaches = coachRepository.findAll();
        return coaches.stream()
                .map(coachMapper::toDTOCoachIgnoreGroupChildClients)
                .toList();
    }

    @Transactional
    @Override
    public CoachDto save(CoachDto entity) {
        return coachMapper.toDTOCoachIgnoreGroupChildClients(
                coachRepository.save(coachMapper.dtoToEntity(entity))
        );
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        coachRepository.deleteById(id);
    }
}
