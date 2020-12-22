package ru.itis.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

import static ru.itis.filters.ResponseUtil.sendForbidden;

public class CsrfFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (request.getMethod().equals("POST")) {
            String requestCsrf = request.getParameter("_csrf_token");
            Set<String> sessionCsrf = (Set<String>) request.getSession(false).getAttribute("_csrf_token");
            if (sessionCsrf.contains(requestCsrf)) {
                System.out.println("I work" + requestCsrf);
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            } else {
                sendForbidden(request, response);
                return;
            }
        }
        if (request.getMethod().equals("GET")) {
            String csrf = UUID.randomUUID().toString();
            Set<String> setCSFR;
            if(request.getSession().getAttribute("_csrf_token") != null) {
                setCSFR = (Set<String>) request.getSession().getAttribute("_csrf_token");
                if(request.getAttribute("_csrf_token") != null) {
                    setCSFR.remove(request.getAttribute("_csrf_token"));
                }
            }else {
                setCSFR = new HashSet<>();
            }
            setCSFR.add(csrf);
            request.setAttribute("_csrf_token", csrf);
            request.getSession().setAttribute("_csrf_token", setCSFR);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
