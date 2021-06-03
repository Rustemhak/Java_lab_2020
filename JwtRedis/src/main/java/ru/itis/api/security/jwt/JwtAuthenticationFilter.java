package ru.itis.api.security.jwt;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.itis.api.exception.AccessTokenIncorrect;
import ru.itis.api.redis.repository.TokensRepository;
import ru.itis.api.repository.UsersRepository;
import ru.itis.api.services.JwtBlacklistService;
import ru.itis.api.services.RefreshService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 05.04.2021
 * 21. REST API
 *
 * @author Sidikov Marsel (First Software Engineering Platform)
 * @version v1.0
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    @Autowired
    public RefreshService refreshService;

    @Autowired
    public TokensRepository tokensRepository;

    @Autowired
    public UsersRepository usersRepository;

    @Autowired
    public JwtBlacklistService jwtBlackListService;

    @SneakyThrows
    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        String token = request.getHeader("ACCESS-TOKEN");
        if (token != null) {
            if (refreshService.accessCheck(token)) {
                JwtAuthentication tokenAuthentication = new JwtAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(tokenAuthentication);
                filterChain.doFilter(request, response);
            } else {
                throw new AccessTokenIncorrect();
            }
        }
        filterChain.doFilter(request, response);
    }
}
