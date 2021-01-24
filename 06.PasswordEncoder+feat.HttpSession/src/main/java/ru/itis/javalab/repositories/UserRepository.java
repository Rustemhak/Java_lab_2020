package ru.itis.javalab.repositories;

import ru.itis.javalab.models.User;

import java.util.List;

public interface UserRepository extends CrudRepository<User> {
    List<User> findAllByAge(int age);
    User findUserByFirstName(String firstName);
    User findUserByToken(String token);
}
