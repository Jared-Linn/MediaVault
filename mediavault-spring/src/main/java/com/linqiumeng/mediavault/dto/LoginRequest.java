package com.linqiumeng.mediavault.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
    private String phone;

}