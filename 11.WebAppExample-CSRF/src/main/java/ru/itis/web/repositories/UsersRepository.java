package ru.itis.web.repositories;

import ru.itdrive.web.models.User;

import java.util.Optional;

public interface UsersRepository extends CrudRepository<User> {
    Optional<User> findOneByEmail(String email);
}
