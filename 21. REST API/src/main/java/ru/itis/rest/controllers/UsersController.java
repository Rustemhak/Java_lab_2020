package ru.itis.rest.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.rest.dto.TeacherDto;
import ru.itis.rest.dto.UserDto;
import ru.itis.rest.services.TeachersService;
import ru.itis.rest.services.UsersService;
@RestController
public class UsersController {
    @Autowired
    private UsersService usersService;
    @ApiOperation(value = "Добавление пользователя")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Успешно добавлено", response = UserDto.class)})
    @PostMapping("/users")
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto user) {
        return ResponseEntity.ok(usersService.addUser(user));
    }
}
