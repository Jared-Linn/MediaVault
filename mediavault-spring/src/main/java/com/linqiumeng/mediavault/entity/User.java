package com.linqiumeng.mediavault.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
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

    // Getters and Setters

}
