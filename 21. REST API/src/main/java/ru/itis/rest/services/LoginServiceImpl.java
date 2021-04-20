package ru.itis.rest.services;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import ru.itis.rest.dto.EmailPasswordDto;
import ru.itis.rest.dto.TokenDto;
import ru.itis.rest.models.Token;
import ru.itis.rest.models.User;
import ru.itis.rest.repositories.TokensRepository;
import ru.itis.rest.repositories.UsersRepository;

import java.util.UUID;
import java.util.function.Supplier;

/**
 * 05.04.2021
 * 21. REST API
 *
 * @author Sidikov Marsel (First Software Engineering Platform)
 * @version v1.0
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokensRepository tokensRepository;

    @SneakyThrows
    @Override
    public TokenDto login(EmailPasswordDto emailPassword) {
        User user = usersRepository.findByEmail(emailPassword.getEmail())
                .orElseThrow((Supplier<Throwable>) () -> new UsernameNotFoundException("User not found"));
        if (passwordEncoder.matches(emailPassword.getPassword(), user.getHashPassword())) {
            String tokenValue = UUID.randomUUID().toString();
            Token token = Token.builder()
                    .token(tokenValue)
                    .user(user)
                    .build();

            String jwtToken = JWT.create()
                    .withSubject(user.getId().toString())
                    .withClaim("role", user.getRole().toString())
                    .withClaim("state", user.getState().toString())
                    .withClaim("email", user.getEmail())
                    .sign(Algorithm.HMAC256("seckret_key"));

            System.out.println(token);
//            //добавить token jwt
//            try {
//                DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256("seckret_key"))
//                        .build()
//                        .verify(token);
//
//                System.out.println(decodedJWT.getClaim("role"));
//                System.out.println(decodedJWT.getClaim("state"));
//                System.out.println(decodedJWT.getClaim("email"));
//            } catch (JWTVerificationException e) {
//                System.err.println(e.getMessage());
//            }

            tokensRepository.save(token);

            return TokenDto.builder()
                    .token(tokenValue)
                    .build();
        } else {
            throw new UsernameNotFoundException("Invalid username or password");
        }


    }
}
