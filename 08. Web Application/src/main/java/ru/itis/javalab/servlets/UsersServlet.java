package ru.itis.javalab.servlets;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private PasswordEncoder passwordEncoder;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        PrintWriter writer = response.getWriter();
//        writer.println("<h1> Users page</h1>");
        if (request.getSession(false) != null) {
            System.out.println(request.getSession(false).getAttribute("Hello"));
        } else {
            HttpSession session = request.getSession();
            session.setAttribute("Hello", "Hello from server!");
        }
        // false - если сессии не было, то вернет null
        // если true, то он создаст сессию и вернет ее
        // если ничего не указано, то либо вернет существующую, либо создаст новую

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
        this.passwordEncoder = (PasswordEncoder) servletContext.getAttribute("passwordEncoder");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String password = request.getParameter("password");
        String hashPassword = passwordEncoder.encode(password);
        System.out.println(hashPassword);
        System.out.println(passwordEncoder.matches("qwerty007", hashPassword));
        String color = request.getParameter("color");
        Cookie cookie = new Cookie("color", color);
        cookie.setMaxAge(60 * 60 * 24 * 365);
        response.addCookie(cookie);
        response.sendRedirect("/users");


    }
}
