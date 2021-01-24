package ru.itis.javalab.repositories;

import java.util.List;
import java.util.Optional;

/**
 * 05.10.2020
 * 08. Web Application
 *
 * @author Sidikov Marsel (First Software Engineering Platform)
 * @version v1.0
 */
public interface CrudRepository<T> {
    void save(T entity);
    void update(T entity);
    void delete(T entity);
    
    Optional<T> findById(Long id);
    List<T> findAll();

}
