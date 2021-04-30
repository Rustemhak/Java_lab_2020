package ru.itis.rest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.rest.models.JwtToken;
import ru.itis.rest.models.Token;

import java.util.Optional;

public interface JwtTokensRepository extends JpaRepository<JwtToken, Long> {
    Optional<JwtToken> findByJwtToken(String jwtToken);

}
