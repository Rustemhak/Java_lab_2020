package ru.itis.javalab.services;


import ru.itis.javalab.dto.UserSignUpForm;

public interface SignUpService {
    void signUp(UserSignUpForm form);
}
