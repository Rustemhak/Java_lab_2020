package ru.itis.javalab.dto;

import lombok.Data;
import ru.itis.javalab.validation.ValidPassword;

import javax.validation.constraints.Email;

@Data
public class UserSignInForm {

    @Email(message = "{errors.incorrect.email}")
    private String email;

    @ValidPassword(message = "{errors.incorrect.password}")
    private String password;
}
