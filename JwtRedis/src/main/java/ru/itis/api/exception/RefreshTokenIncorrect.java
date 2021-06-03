package ru.itis.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class RefreshTokenIncorrect extends RuntimeException {
    public RefreshTokenIncorrect() {
        super("Log in again");
    }
}