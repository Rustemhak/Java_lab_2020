package ru.itis.javalab.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.itis.javalab.models.User;

import java.util.List;
import java.util.Optional;


public interface  UsersRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findAllByFirstNameIsLikeIgnoreCaseAndAgeAfter(String firstName, int age);

    @Query("select user from User user where user.age >= :age " +
            "and (upper(user.firstName) like concat('%', upper(:name), '%') " +
            "       or upper(user.lastName) like  concat('%', upper(:name), '%'))")
    List<User> search(@Param("name") String name, @Param("age") Integer age);
}
