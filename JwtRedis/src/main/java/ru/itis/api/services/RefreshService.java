package ru.itis.api.services;

import com.auth0.jwt.interfaces.DecodedJWT;
import ru.itis.api.models.User;

import java.util.List;

public interface RefreshService {

    boolean timeCheck(DecodedJWT decodedJWT, long limit);

    boolean accessCheck(String accessToken);
    boolean refreshCheck(String refreshToken);

    List<String> generateTokens(User user);

}