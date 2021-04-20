package ru.itis.rest.services;

import ru.itis.rest.dto.UserDto;

public interface UsersService {
    UserDto addUser(UserDto user);
}
