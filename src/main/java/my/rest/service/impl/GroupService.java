package my.rest.service.impl;

import my.rest.dto.GroupDto;
import my.rest.model.Group;
import my.rest.repository.GroupRepository;
import my.rest.service.SimpleService;
import my.rest.service.mapping.GroupMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
public class GroupService implements SimpleService<GroupDto> {

    private final GroupRepository groupsRepository;
    private final GroupMapper groupsMapper = Mappers.getMapper(GroupMapper.class);

    public GroupService(GroupRepository groupsRepository) {
        this.groupsRepository = groupsRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public GroupDto findById(Long id) throws SQLException {
        return groupsMapper.toDTOGroupIgnoreClientsAndCoach(
                groupsRepository.findById(id).orElseThrow(SQLException::new)
        );
    }

    @Transactional(readOnly = true)
    @Override
    public List<GroupDto> findAll() {
        List<Group> groups = groupsRepository.findAll();
        return groups.stream()
                .map(groupsMapper::toDTOGroupIgnoreClientsAndCoach)
                .toList();
    }

    @Transactional
    @Override
    public GroupDto save(GroupDto entity) {
        return groupsMapper.toDTOGroupIgnoreClientsAndCoach(
                groupsRepository.save(groupsMapper.dtoToEntity(entity))
        );
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        groupsRepository.deleteById(id);
    }
}
