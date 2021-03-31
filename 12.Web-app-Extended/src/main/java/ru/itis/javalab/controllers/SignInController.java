package ru.itis.javalab.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.itis.javalab.dto.UserSignInForm;
import ru.itis.javalab.services.UsersService;

@Controller
public class SignInController {


    @PreAuthorize("permitAll()")
    @RequestMapping(value = "/signIn", method = RequestMethod.GET)
    public String getSignInPage(Model model) {
        model.addAttribute("userSignInForm", new UserSignInForm());
        return "sign_in_page";

    }
}
