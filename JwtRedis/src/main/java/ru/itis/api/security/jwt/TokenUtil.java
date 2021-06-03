package ru.itis.api.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.Data;
import ru.itis.api.models.User;

import java.util.Date;

@Data
public class TokenUtil {

    private String accessToken;

    private Date accessTime;

    private Date refreshTime;

    private String refreshToken;

    public TokenUtil(User user) {


        Date date = new Date();

        this.accessToken = JWT.create()
                .withSubject(user.getId().toString())
                .withClaim("role", user.getRole().toString())
                .withClaim("state", user.getState().toString())
                .withClaim("email", user.getEmail())
                .withExpiresAt(new Date(date.getTime() + 3000))
                .sign(Algorithm.HMAC256("secret"));


        this.refreshToken = JWT.create()
                .withSubject(user.getId().toString())
                .withExpiresAt(new Date(date.getTime() + 604800000))
                .sign(Algorithm.HMAC256("secret"));

        refreshTime = new Date();

    }

}