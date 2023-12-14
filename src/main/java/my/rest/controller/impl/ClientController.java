package my.rest.controller.impl;

import my.rest.controller.SimpleController;
import my.rest.dto.ClientDto;
import my.rest.service.SimpleService;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController implements SimpleController<ClientDto> {

    private final SimpleService<ClientDto> clientService;

    public ClientController(SimpleService<ClientDto> clientService) {
        this.clientService = clientService;
    }

    @Override
    @GetMapping(path = "/{id}", produces = {"application/json"})
    public ClientDto findById(@PathVariable(name = "id") Long id) throws SQLException {
        return clientService.findById(id);
    }

    @Override
    @GetMapping
    public List<ClientDto> findAll() {
        return (List<ClientDto>) clientService.findAll();
    }

    @Override
    @PostMapping(consumes = {"application/json"}, produces = "application/json")
    public ClientDto save(@RequestBody ClientDto entity) {
        return clientService.save(entity);
    }

    @Override
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable(name = "id") Long id) {
        clientService.deleteById(id);
    }
}
