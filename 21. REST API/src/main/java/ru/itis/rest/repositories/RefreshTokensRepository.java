package ru.itis.rest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.rest.models.JwtToken;
import ru.itis.rest.models.RefreshToken;

import java.util.Optional;

public interface RefreshTokensRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

}
