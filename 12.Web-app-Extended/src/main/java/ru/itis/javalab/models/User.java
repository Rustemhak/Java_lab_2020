package ru.itis.javalab.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 08.10.2020
 * 05. WebApp
 *
 * @author Sidikov Marsel (First Software Engineering Platform)
 * @version v1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="account")
public class User implements Serializable {
    private static final long serialVersionUID = -2421697643133005423L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String patronymic;
    private int age;
    private int token;
    private String email;
    private String hashPassword;
    @Enumerated(value = EnumType.STRING)
    private State state;

    private String confirmCode;
    public enum State {
        CONFIRMED, NOT_CONFIRMED,
        ACTIVE,BANNED
    }
    @Enumerated(value = EnumType.STRING)
    private Role role;
    public enum Role{
        USER, ADMIN
    }
    public boolean isActive(){
        return this.state == State.ACTIVE;
    }
    public boolean isBanned(){
        return this.state == State.BANNED;
    }
    public boolean isAdmin(){
        return this.role == Role.ADMIN;
    }
    @OneToMany(mappedBy = "owner")
    private List<Product> products;
}
