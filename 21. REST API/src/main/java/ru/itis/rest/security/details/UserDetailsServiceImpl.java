package ru.itis.rest.security.details;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.itis.rest.models.JwtToken;
import ru.itis.rest.models.RefreshToken;
import ru.itis.rest.models.Token;
import ru.itis.rest.models.User;
import ru.itis.rest.repositories.JwtTokensRepository;
import ru.itis.rest.repositories.RefreshTokensRepository;
import ru.itis.rest.repositories.TokensRepository;
import ru.itis.rest.repositories.UsersRepository;

import java.util.function.Supplier;

import static ru.itis.rest.models.User.*;
import static java.lang.System.*;

/**
 * 13.03.2021
 * 03. Spring Security
 *
 * @author Sidikov Marsel (First Software Engineering Platform)
 * @version v1.0
 */
@Component("tokenUserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private RefreshTokensRepository refreshTokensRepository;



    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String token) throws UsernameNotFoundException {
//        Token result = tokensRepository.findByToken(token).orElseThrow((Supplier<Throwable>) () -> new UsernameNotFoundException("Token not found"));
        RefreshToken result = refreshTokensRepository.findByToken(token).orElseThrow((Supplier<Throwable>) () -> new UsernameNotFoundException("Token not found"));
        boolean expiredToken =true;
        try {
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256("seckret_key"))
                    .build()
                    .verify(token);
            Role role = Role.valueOf(decodedJWT.getClaim("role").asString());
            State state = State.valueOf(decodedJWT.getClaim("state").asString());
            String email = decodedJWT.getClaim("email").asString();
            if (decodedJWT.getExpiresAt().getTime() < 0) {
                expiredToken = false;
            }
        } catch (JWTVerificationException e) {
            System.err.println(e.getMessage());
        }
        return new UserDetailsImpl(result.getUser(), expiredToken);

    }
}
