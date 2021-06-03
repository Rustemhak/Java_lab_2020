package ru.itis.api.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.itis.api.services.JwtBlacklistService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 09.04.2021
 * 52. JwtRedis
 *
 * @author Sidikov Marsel (First Software Engineering Platform)
 * @version v1.0
 */
@Component
public class JwtLogoutFilter extends OncePerRequestFilter {

    @Autowired
    private JwtBlacklistService jwtTokensBlackListService;

    private final RequestMatcher logoutRequest = new AntPathRequestMatcher("/logout", "GET");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (logoutRequest.matches(request)) {
            jwtTokensBlackListService.add(request.getHeader("ACCESS-TOKEN"));
            jwtTokensBlackListService.add(request.getHeader("REFRESH-TOKEN"));
            jwtTokensBlackListService.deleteToken(request.getHeader("REFRESH-TOKEN"));
            SecurityContextHolder.clearContext();
            return;
        }
        filterChain.doFilter(request, response);
    }
}
