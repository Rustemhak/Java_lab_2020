package ru.itis.rest.services;

import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import ru.itis.rest.dto.EmailPasswordDto;
import ru.itis.rest.dto.JwtTokenDto;
import ru.itis.rest.models.RefreshToken;
import ru.itis.rest.models.User;
import ru.itis.rest.repositories.JwtTokensRepository;
import ru.itis.rest.repositories.RefreshTokensRepository;
import ru.itis.rest.repositories.TokensRepository;
import ru.itis.rest.repositories.UsersRepository;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

import static java.lang.System.*;

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
    @Autowired
    private JwtTokensRepository jwtTokensRepository;
    @Autowired
    private RefreshTokensRepository refreshTokensRepository;

//    @SneakyThrows
//    @Override
//    public TokenDto login(EmailPasswordDto emailPassword) {
//        User user = usersRepository.findByEmail(emailPassword.getEmail())
//                .orElseThrow((Supplier<Throwable>) () -> new UsernameNotFoundException("User not found"));
//        if (passwordEncoder.matches(emailPassword.getPassword(), user.getHashPassword())) {
//            String tokenValue = UUID.randomUUID().toString();
//            Token token = Token.builder()
//                    .token(tokenValue)
//                    .user(user)
//                    .build();
//            tokensRepository.save(token);
//            return TokenDto.builder()
//                    .token(tokenValue)
//                    .build();
//        } else {
//            throw new UsernameNotFoundException("Invalid username or password");
//        }
//    }

    @SneakyThrows
    @Override
    public JwtTokenDto login(EmailPasswordDto emailPassword) {
        User user = usersRepository.findByEmail(emailPassword.getEmail())
                .orElseThrow((Supplier<Throwable>) () -> new UsernameNotFoundException("User not found"));
        if (passwordEncoder.matches(emailPassword.getPassword(), user.getHashPassword())) {
//            //jwt
//            String jwtToken = JWT.create()
//                    .withSubject(user.getId().toString())
//                    .withClaim("role", user.getRole().toString())
//                    .withClaim("state", user.getState().toString())
//                    .withClaim("email", user.getEmail())
//                    .sign(Algorithm.HMAC256("seckret_key"));
//            JwtToken newJwtToken = JwtToken.builder()
//                    .jwtToken(jwtToken)
//                    .user(user)
//                    .build();
//            jwtTokensRepository.save(newJwtToken);
//            System.out.println(jwtToken);
//            return JwtTokenDto.builder()
//                    .jwtToken(jwtToken)
//                    .build();
            String jwtToken = JWT.create()
                    .withSubject(user.getId().toString())
                    .withClaim("role", user.getRole().toString())
                    .withClaim("state", user.getState().toString())
                    .withClaim("email", user.getEmail())
                    .sign(Algorithm.HMAC256("seckret_key"));
            RefreshToken refreshToken = RefreshToken.builder()
                    .token(jwtToken)
                    .timer(new Timestamp(currentTimeMillis() + 9000000))
                    .user(user)
                    .build();
            refreshTokensRepository.save(refreshToken);
            String accessToken = jwtToken;

            return JwtTokenDto.from(refreshToken, accessToken);
        } else {
            throw new UsernameNotFoundException("Invalid username or password");
        }
    }

    @Override
    public JwtTokenDto login(String refreshToken) {
        Optional<RefreshToken> optionalRefreshToken = refreshTokensRepository.findByToken(refreshToken);
        RefreshToken newRefreshToken = optionalRefreshToken.orElseThrow(() -> new UsernameNotFoundException("Token not found"));
        if (newRefreshToken.getTimer().getTime() + 9000000 < currentTimeMillis()) {
            throw new TokenExpiredException("Token was expired");
        }
        newRefreshToken.setToken(UUID.randomUUID().toString());
        refreshTokensRepository.save(newRefreshToken);
        User user = newRefreshToken.getUser();
        String accessToken = JWT.create()
                .withSubject(user.getId().toString())
                .withClaim("role", user.getRole().toString())
                .withClaim("state", user.getState().toString())
                .withClaim("email", user.getEmail())
                .sign(Algorithm.HMAC256("seckret_key"));
        return JwtTokenDto.from(newRefreshToken, accessToken);
    }
}
