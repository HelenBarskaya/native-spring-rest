package my.rest.controller;

import java.sql.SQLException;
import java.util.List;

public interface SimpleController<T> {
    
    T findById(Long id) throws SQLException;

    List<T> findAll();

    T save(T entity);

    void deleteById(Long id);
}
