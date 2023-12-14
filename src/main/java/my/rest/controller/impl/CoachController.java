package my.rest.controller.impl;

import my.rest.controller.SimpleController;
import my.rest.dto.CoachDto;
import my.rest.service.SimpleService;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/coaches")
public class CoachController implements SimpleController<CoachDto> {

    private final SimpleService<CoachDto> coachService;

    public CoachController(SimpleService<CoachDto> coachService) {
        this.coachService = coachService;
    }

    @Override
    @GetMapping(path = "/{id}", produces = {"application/json"})
    public CoachDto findById(@PathVariable(name = "id") Long id) throws SQLException {
        return coachService.findById(id);
    }

    @Override
    @GetMapping
    public List<CoachDto> findAll() {
        return (List<CoachDto>) coachService.findAll();
    }

    @Override
    @PostMapping(consumes = "application/json", produces = "application/json")
    public CoachDto save(@RequestBody CoachDto entity) {
        return coachService.save(entity);
    }

    @Override
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable(name = "id") Long id) {
        coachService.deleteById(id);
    }
}
