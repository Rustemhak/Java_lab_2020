package ru.itis.rest.services;

import ru.itis.rest.dto.EmailPasswordDto;
import ru.itis.rest.dto.JwtTokenDto;
import ru.itis.rest.dto.TokenDto;

/**
 * 05.04.2021
 * 21. REST API
 *
 * @author Sidikov Marsel (First Software Engineering Platform)
 * @version v1.0
 */
public interface LoginService {
//    TokenDto login(EmailPasswordDto emailPassword);
    JwtTokenDto login(EmailPasswordDto emailPassword);
    JwtTokenDto login(String refreshToken);

}
