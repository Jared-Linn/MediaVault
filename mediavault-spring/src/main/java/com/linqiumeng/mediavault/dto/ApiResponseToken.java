package com.linqiumeng.mediavault.dto;

import lombok.Data;

@Data
public class ApiResponseToken<T> {
    private int code;
    private String message;
    private T token;

    public ApiResponseToken(int code, String message, T token) {
        this.code = code;
        this.message = message;
        this.token = token;
    }

    public static <T> ApiResponseToken<T> success(T token) {
        return new ApiResponseToken<>(200, "Success", token);
    }

    public static <T> ApiResponseToken<T> failure(int code, String message) {
        return new ApiResponseToken<>(code, message, null);
    }
}
