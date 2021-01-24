package ru.itis.web.services;

public interface SignInService {
    boolean authenticate(String email, String password);
}
