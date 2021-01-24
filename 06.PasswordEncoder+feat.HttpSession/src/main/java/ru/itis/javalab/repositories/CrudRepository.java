package ru.itis.javalab.repositories;

import java.util.*;

public interface CrudRepository<T> {
    void save(T entity);
    void update(T entity);
    void delete(T entity);
    Optional<T> findById(Long id);
    List<T> findAll();
}
