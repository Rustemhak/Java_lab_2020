package ru.itis.javalab.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.itis.javalab.dto.UserSignUpForm;
import ru.itis.javalab.security.details.UserDetailsImpl;
import ru.itis.javalab.services.SignUpService;

import javax.validation.Valid;
import java.util.Objects;


@Controller
public class SignUpController {
    @Autowired
    private SignUpService signUpService;

    @PreAuthorize("permitAll()")
    @RequestMapping(value = "/success", method = RequestMethod.GET)
    public String getSuccessPage() {
        return "success_signup";
    }

    @PreAuthorize("permitAll()")
    @RequestMapping(value = "/signUp", method = RequestMethod.GET)
    public String getSignUpPage(Model model) {
        model.addAttribute("userSignUpForm", new UserSignUpForm());
        return "sign_up_page";
    }

    @PreAuthorize("permitAll()")

    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public String signUp(@Valid UserSignUpForm form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().stream().anyMatch(error -> {
                if (Objects.requireNonNull(error.getCodes())[0].equals("userForm.ValidNames")) {
                    model.addAttribute("namesErrorMessage", error.getDefaultMessage());
                }
                return true;
            });
            model.addAttribute("userSignUpForm", form);
            return "sign_up_page";
        }
        signUpService.signUp(form);
        return "redirect:/signIn";
    }
}
