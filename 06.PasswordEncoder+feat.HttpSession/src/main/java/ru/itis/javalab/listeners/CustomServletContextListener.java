package ru.itis.javalab.listeners;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.WebappTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.itis.javalab.repositories.UserRepository;
import ru.itis.javalab.repositories.UserRepositoryJdbcImpl;
import ru.itis.javalab.services.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.util.Properties;
@WebListener
public class CustomServletContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();

        Properties properties = new Properties();
        try {
            properties.load(servletContext.getResourceAsStream("/WEB-INF/db.properties"));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        // hicari
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(properties.getProperty("db.url"));
        hikariConfig.setDriverClassName(properties.getProperty("db.driver.classname"));
        hikariConfig.setUsername(properties.getProperty("db.username"));
        hikariConfig.setPassword(properties.getProperty("db.password"));
        hikariConfig.setMaximumPoolSize(Integer.parseInt(properties.getProperty("db.hikari.max-pool-size")));
        HikariDataSource dataSource = new HikariDataSource(hikariConfig);
        servletContext.setAttribute("dataSource", dataSource);

        // encoder
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        servletContext.setAttribute("passwordEncoder", passwordEncoder);

        //userRepository
        UserRepository userRepository = new UserRepositoryJdbcImpl(dataSource);

        //authService
        AuthService authService = new AuthServiceImpl();
        servletContext.setAttribute("authService", authService);

        //userService
        UserService userService = new UserServiceImpl(userRepository, authService, passwordEncoder);
        servletContext.setAttribute("userService", userService);

        //freemarker conf
        Configuration freemarkerConfiguration = new Configuration(Configuration.VERSION_2_3_21);
        freemarkerConfiguration.setTemplateLoader(new WebappTemplateLoader(servletContext,"/ftl/"));
        servletContext.setAttribute("freemarkerConfiguration", freemarkerConfiguration);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

}
