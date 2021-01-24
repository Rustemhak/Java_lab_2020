package ru.itis.javalab.filters;

import ru.itis.javalab.constants.Constants;
import ru.itis.javalab.services.UserService;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

public class AuthFilter implements Filter {


    private UserService userService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ServletContext servletContext = filterConfig.getServletContext();
        this.userService = (UserService) servletContext.getAttribute("userService");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String uri = request.getRequestURI();

        String token = Optional.ofNullable(request.getCookies())
                .flatMap(array -> Arrays.stream(array)
                        .filter(cookie -> cookie.getName().equals(Constants.AUTH_COOKIE_NAME))
                        .map(Cookie::getValue)
                        .findAny())
                .orElse(null);

        boolean isTokenCorrect = this.userService.isTokenCorrect(token);
        if (!isTokenCorrect && !uri.equals("/login")) {
            response.sendRedirect("/login");
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }

        /*if (!uri.equals("/registration") && !uri.equals("/login")) {
            HttpSession session = request.getSession(false);
        }*/
    }

    @Override
    public void destroy() {

    }
}
