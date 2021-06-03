package ru.itis.api;

import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.itis.api.security.jwt.RefreshTokenFilter;

@SpringBootApplication
public class JwtRedisApplication {

    @Autowired
    private final RefreshTokenFilter refreshTokenFilter;

    public JwtRedisApplication(RefreshTokenFilter refreshTokenFilter) {
        this.refreshTokenFilter = refreshTokenFilter;
    }

    @Bean
    FilterRegistrationBean<RefreshTokenFilter> registrationBean() {
        final FilterRegistrationBean<RefreshTokenFilter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(this.refreshTokenFilter);
        filterFilterRegistrationBean.addUrlPatterns("/refresh");
        return filterFilterRegistrationBean;
    }


    @Value("${jwt.secret}")
    private String secret;

    @Bean
    public Algorithm algorithm() {
        return Algorithm.HMAC256(secret);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {
        SpringApplication.run(JwtRedisApplication.class, args);
    }

}
