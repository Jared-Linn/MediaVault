package com.linqiumeng.mediavault.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String name;
    private String password;
}