package ru.itis.rest.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.rest.dto.UserDto;
import ru.itis.rest.models.User;
import ru.itis.rest.repositories.UsersRepository;

import static ru.itis.rest.dto.UserDto.from;


@Service
public class UsersServiceImpl implements UsersService {
    @Autowired
    private UsersRepository usersRepository;

    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDto addUser(UserDto user) {
        User newUser = User.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getLastName())
                .role(User.Role.ADMIN)
                .state(User.State.ACTIVE)
                .id(user.getId())
                .build();
        usersRepository.save(newUser);
        String token = JWT.create()
                .withSubject(user.getId().toString())
                .withClaim("role", newUser.getRole().toString())
                .withClaim("state", newUser.getState().toString())
                .withClaim("email", newUser.getEmail())
                .sign(Algorithm.HMAC256("seckret_key"));

        System.out.println(token);
        return from(newUser);
    }

}
