package my.rest.controller.impl;

import my.rest.controller.SimpleController;
import my.rest.dto.GroupDto;
import my.rest.service.SimpleService;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/groups")
public class GroupController implements SimpleController<GroupDto> {

    SimpleService<GroupDto> groupService;

    public GroupController(SimpleService<GroupDto> groupService) {
        this.groupService = groupService;
    }

    @Override
    @GetMapping(path = "/{id}", produces = "application/json")
    public GroupDto findById(@PathVariable(name = "id") Long id) throws SQLException {
        return groupService.findById(id);
    }

    @Override
    @GetMapping
    public List<GroupDto> findAll() {
        return (List<GroupDto>) groupService.findAll();
    }

    @Override
    @PostMapping(consumes = "application/json", produces = "application/json")
    public GroupDto save(@RequestBody GroupDto entity) {
        return groupService.save(entity);
    }

    @Override
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable(name = "id") Long id) {
        groupService.deleteById(id);
    }
}

