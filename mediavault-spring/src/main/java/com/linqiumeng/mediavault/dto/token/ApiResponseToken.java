package com.linqiumeng.mediavault.dto.token;


import com.linqiumeng.mediavault.dto.ApiResponse;

public class ApiResponseToken<T> extends ApiResponse<T> {
    public ApiResponseToken(int code, String message, T data) {
        super(code, message, data);
    }

    public static <T> ApiResponseToken<T> success(T data) {
        return new ApiResponseToken<>(200, "Success", data);
    }

    public static <T> ApiResponseToken<T> failure(int code, String message) {
        return new ApiResponseToken<>(code, message, null);
    }
}
