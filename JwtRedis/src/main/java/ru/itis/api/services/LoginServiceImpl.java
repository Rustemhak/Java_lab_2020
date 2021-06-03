package ru.itis.api.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.api.dto.EmailPasswordDto;
import ru.itis.api.dto.TokenDto;
import ru.itis.api.models.Token;
import ru.itis.api.models.User;
import ru.itis.api.redis.repository.TokensRepository;
import ru.itis.api.redis.services.RedisUsersService;
import ru.itis.api.repository.UsersRepository;
import ru.itis.api.security.jwt.TokenUtil;

import java.time.LocalDateTime;
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
    private TokensRepository tokensRepository;

    @Autowired
    private Algorithm algorithm;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RedisUsersService redisUsersService;

    @SneakyThrows
    @Override
    public TokenDto login(EmailPasswordDto emailPassword) {
        User user = usersRepository.findByEmail(emailPassword.getEmail())
                .orElseThrow((Supplier<Throwable>) () -> new UsernameNotFoundException("User not found"));
        if (passwordEncoder.matches(emailPassword.getPassword(), user.getHashPassword())) {
            TokenUtil tokensUtil = new TokenUtil(user);
            Token token = Token.builder()
                    .refreshToken(tokensUtil.getRefreshToken())
                    .user(user)
                    .tokensUtil(tokensUtil)
                    .time_of_creating(tokensUtil.getRefreshTime())
                    .build();
            tokensRepository.save(token);
            redisUsersService.addTokenToUser(user, tokensUtil.getRefreshToken());
            return TokenDto.builder()
                    .accessToken(tokensUtil.getAccessToken())
                    .refreshToken(tokensUtil.getRefreshToken())
                    .build();
        } else {
            throw new UsernameNotFoundException("Invalid username or password");
        }
    }
}
