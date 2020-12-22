package ru.itis.javalab.services;

import ru.itis.javalab.models.User;

import java.util.List;

public interface UserService {
    boolean containsUserAuth(String uuid);
    List<User> getUsersByUUID(String uuid);
    String getUUIDByCredentials(String username, String password);
    void addUser(User user);
    String getPasswordByUsername(String username);
    User getUserByUserName(String username);
    boolean containsUser(String username, String hashPassword);

}
