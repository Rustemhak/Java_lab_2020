package ru.itis.javalab.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import ru.itis.javalab.models.User;
import ru.itis.javalab.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

public class UserServiceImpl implements UserService {

    private final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private AuthService authService;

    public UserServiceImpl(UserRepository userRepository, AuthService authService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authService = authService;
        this.passwordEncoder = passwordEncoder;
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
        this.userRepository.save(user);
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
    public boolean containsUser(String firstName, String rawPassword) {
        User user = userRepository.findUserByFirstName(firstName);
        return passwordEncoder.matches(rawPassword, user.getHashPassword());
    }

    @Override
    public String addTokenToUser(String firstName) {
        User user = userRepository.findUserByFirstName(firstName);
        String token = authService.generateTokenForUser();
        user.setToken(token);
        userRepository.update(user);
        return token;
    }

    @Override
    public boolean isTokenCorrect(String token) {
        if (token == null) return false;
        try {
            return userRepository.findUserByToken(token) != null;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
