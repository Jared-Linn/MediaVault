package com.linqiumeng.mediavault.dto;


public class UserResponse {
    private Integer id;
    private String name;
    private String message;

    public UserResponse(Integer id, String name, String message) {
        this.id = id;
        this.name = name;
        this.message = message;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
