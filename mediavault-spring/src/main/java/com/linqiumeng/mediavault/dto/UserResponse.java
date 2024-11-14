package com.linqiumeng.mediavault.dto;


public class UserResponse {
    private Integer id;
    private String username;
    private String message;

    public UserResponse(Integer id, String username, String message) {
        this.id = id;
        this.username = username;
        this.message = message;
    }

    // Getters and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
