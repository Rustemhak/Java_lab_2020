package ru.itis.javalab.services;

import java.util.UUID;

public class AuthServiceImpl implements AuthService {
    @Override
    public String generateTokenForUser() {
        return UUID.randomUUID().toString();
    }
}
