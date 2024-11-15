package com.linqiumeng.mediavault.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;
    private String phone;




    public User(String name, String password) {
        this.username = username;
        this.password = password;
        this.phone = phone;
    }

    public User() {

    }

    public User orElse(Object o) {
        return null;
    }

    // Getters and Setters

}
