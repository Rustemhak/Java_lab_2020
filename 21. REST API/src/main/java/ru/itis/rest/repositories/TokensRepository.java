package ru.itis.rest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.rest.models.Token;

import java.util.Optional;

/**
 * 05.04.2021
 * 21. REST API
 *
 * @author Sidikov Marsel (First Software Engineering Platform)
 * @version v1.0
 */
public interface TokensRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByToken(String token);
}
