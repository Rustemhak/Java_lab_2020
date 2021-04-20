package ru.itis.rest.security.details;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.itis.rest.models.Token;
import ru.itis.rest.repositories.TokensRepository;

import java.util.function.Supplier;

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
    private TokensRepository tokensRepository;

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String token) throws UsernameNotFoundException {
        Token result = tokensRepository.findByToken(token).orElseThrow((Supplier<Throwable>) () -> new UsernameNotFoundException("Token not found"));
        return new UserDetailsImpl(result.getUser());
    }
}
