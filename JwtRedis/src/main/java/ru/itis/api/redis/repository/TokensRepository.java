package ru.itis.api.redis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.itis.api.models.Token;

import java.util.Optional;

@EnableJpaRepositories
public interface TokensRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByRefreshToken(String token);

    void deleteByUser_Id(Long id);

    Optional<Token> findByUser_Id(Long id);

    void deleteByRefreshToken(String token);

}