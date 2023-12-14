package my.rest.service;

import java.sql.SQLException;

public interface SimpleService<T> {

    T findById(Long id) throws SQLException;

    Iterable<T> findAll();

    T save(T entity);

    void deleteById(Long id);
}
