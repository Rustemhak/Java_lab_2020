package ru.itis.javalab.servlets;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import ru.itis.javalab.services.UserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/users")
public class UsersServlet extends HttpServlet {

    private UserService userService;
    private Configuration configuration;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Template template = configuration.getTemplate("users.ftl");
        try {
            Map<String, Object> data = new HashMap<>();
            data.put("usersForJsp", userService.getAllUsers());
            template.process(data, response.getWriter());
        } catch (TemplateException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        this.userService = (UserService) servletContext.getAttribute("userService");
        this.configuration = (Configuration) servletContext.getAttribute("freemarkerConfiguration");
    }
}
