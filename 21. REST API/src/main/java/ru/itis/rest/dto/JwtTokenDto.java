package ru.itis.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.rest.models.JwtToken;
import ru.itis.rest.models.RefreshToken;

/**
 * 05.04.2021
 * 21. REST API
 *
 * @author Sidikov Marsel (First Software Engineering Platform)
 * @version v1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtTokenDto {
    private String jwtToken;
    private String refreshToken;
    private String accessToken;

    public static JwtTokenDto from(RefreshToken refreshToken, String accessToken) {
        return JwtTokenDto.builder()
                .refreshToken(refreshToken.getToken())
                .accessToken(accessToken)
                .build();
    }
}
