package com.doto.doto.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    private Long id;
    private String email;
    private String password;
    private String nickname;
    private String username;

}
