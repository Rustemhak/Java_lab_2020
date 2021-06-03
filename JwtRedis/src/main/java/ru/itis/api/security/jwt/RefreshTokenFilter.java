package ru.itis.api.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.itis.api.exception.RefreshTokenIncorrect;
import ru.itis.api.services.JwtBlacklistService;
import ru.itis.api.services.RefreshService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RefreshTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RefreshService refreshService;

    @Autowired
    private JwtBlacklistService jwtBlackListService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String token = request.getHeader("REFRESH-TOKEN");
        if (token != null) {

            System.out.println(token);

            if (jwtBlackListService.exists(token)) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            if (refreshService.refreshCheck(token)) {
                filterChain.doFilter(request, response);
            } else {
                throw new RefreshTokenIncorrect();
            }
        }
    }
}