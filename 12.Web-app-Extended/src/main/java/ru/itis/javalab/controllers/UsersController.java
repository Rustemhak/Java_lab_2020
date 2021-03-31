package ru.itis.javalab.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itis.javalab.dto.UserDto;
import ru.itis.javalab.services.UsersService;

import java.util.List;

/**
 * 19.11.2020
 * 14. Web MVC Extended
 *
 * @author Sidikov Marsel (First Software Engineering Platform)
 * @version v1.0
 */
@Controller
public class UsersController {

    @Autowired
    private UsersService usersService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String getUsersPage(@RequestParam(value = "page", required = false) Integer page,
                               @RequestParam(value = "size", required = false) Integer size,
                               Model model) {
        if (page != null && size != null) {
            model.addAttribute("users", usersService.getAllUsers(page, size));
        } else {
            model.addAttribute("users", usersService.getAllUsers());
        }
        return "users_view";
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public String addUser(UserDto user) {
        usersService.addUser(user);
        return "redirect:/users";
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/banAll", method = RequestMethod.POST)
    public String banUsers() {
        usersService.banAll();
        return "redirect:/users";
    }
    @RequestMapping(value = "/users/{user-id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public UserDto getUser(@PathVariable("user-id") Long userId) {
        return usersService.getUser(userId);
    }

    @RequestMapping(value = "/users/json", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<UserDto> addUserFromJson(@RequestBody UserDto user) {
        usersService.addUser(user);
        return usersService.getAllUsers();
    }

}
