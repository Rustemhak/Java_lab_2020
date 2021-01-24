package ru.itis.web.services;

import ru.itdrive.web.models.User;

import java.util.Optional;

public interface UsersService {
    Optional<User> getUserById(Long id);

    void deleteUserById(long userId);
}
