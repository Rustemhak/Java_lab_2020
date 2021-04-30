package ru.itis.rest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.rest.models.User;

import java.util.Optional;

/**
 * 10.02.2021
 * spring-boot-demo
 *
 * @author Sidikov Marsel (First Software Engineering Platform)
 * @version v1.0
 */
public interface UsersRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByRoleAndStateAndEmail(User.Role role, User.State state, String email);

}
