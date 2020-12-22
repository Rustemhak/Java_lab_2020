package ru.itis.javalab.services;

import ru.itis.javalab.models.User;
import ru.itis.javalab.repositories.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

public class UserServiceImpl implements UserService {
    private UsersRepository usersRepository;
    private final Logger LOG = LoggerFactory.getLogger(UserService.class);
    public UserServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }


    @Override
    public boolean containsUserAuth(String uuid) {
        return !getUsersByUUID(uuid).isEmpty();
    }

    @Override
    public List<User> getUsersByUUID(String uuid) {
        return null;
    }

    @Override
    public String getUUIDByCredentials(String username, String password) {
        return null;
    }

    @Override
    public void addUser(User user) {
        this.usersRepository.save(user);
    }

    @Override
    public String getPasswordByUsername(String username) {
        return null;
    }

    @Override
    public User getUserByUserName(String username) {
        return null;
    }

    @Override
    public boolean containsUser(String username, String hashPassword) {
        return false;
    }
}
