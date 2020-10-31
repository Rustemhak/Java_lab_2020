package ru.itis.javalab.servlets;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ru.itis.javalab.models.User;
import ru.itis.javalab.repositories.UsersRepository;
import ru.itis.javalab.repositories.UsersRepositoryJdbcImpl;
import ru.itis.javalab.services.UserService;
import ru.itis.javalab.services.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import javax.sql.DataSource;

import java.io.*;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/users")

public class UsersServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        PrintWriter writer = response.getWriter();
//        writer.println("<h1> Users page</h1>");

        List<User> users = new ArrayList<>();
        users.add(User.builder()
                .id(1L)
                .firstName("Марсель")
                .lastName("Сидиков")
                .age(26)
                .build());
        users.add(User.builder()
                .id(2L)
                .firstName("Расим")
                .lastName("Гарипов")
                .age(19)
                .build());

        request.setAttribute("usersForJsp", users);
        request.getRequestDispatcher("/jsp/users.jsp").forward(request, response);

    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        this.userService = (UserService) servletContext.getAttribute("userService");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String color = request.getParameter("color");
        Cookie cookie = new Cookie("color", color);
        cookie.setMaxAge(60 * 60 * 24 * 365);
        response.addCookie(cookie);
        response.sendRedirect("/users");

    }
}
