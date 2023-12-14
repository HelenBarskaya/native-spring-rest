package my.rest.repository;

import my.rest.model.Coach;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoachRepository extends CrudRepository<Coach, Long> {
    @Override
    List<Coach> findAll();
}
