package ru.itis.api.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.api.security.jwt.TokenUtil;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String refreshToken;

    private Date time_of_creating;

    @Transient
    private TokenUtil tokensUtil;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Token(long parseLong, String new_refresh_token, Date date) {
        id = parseLong;
        refreshToken = new_refresh_token;
        time_of_creating = date;
    }

    public Token(String new_refresh_token, Date date, User user) {
        this.user = user;
        refreshToken = new_refresh_token;
        time_of_creating = date;
    }

    public User getUser() {
        return user;
    }

}