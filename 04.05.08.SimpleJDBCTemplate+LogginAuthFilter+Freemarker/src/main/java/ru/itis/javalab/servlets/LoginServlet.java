package ru.itis.javalab.servlets;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import ru.itis.javalab.constants.Constants;
import ru.itis.javalab.services.UserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UserService userService;
    private Configuration configuration;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        this.userService = (UserService) servletContext.getAttribute("userService");
        this.configuration = (Configuration) servletContext.getAttribute("freemarkerConfiguration");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Template template = configuration.getTemplate("login.ftl");
        try {
            template.process(null, response.getWriter());
        } catch (TemplateException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstName = req.getParameter("first_name");
        String token = userService.addTokenToUser(firstName);
        resp.addCookie(new Cookie(Constants.AUTH_COOKIE_NAME, token));
        resp.sendRedirect("/users");
    }
}
