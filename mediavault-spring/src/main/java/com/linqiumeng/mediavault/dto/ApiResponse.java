package com.linqiumeng.mediavault.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

//响应类
@Setter
@Getter
@Data
public class ApiResponse<T> {
    private int code;
    private String message;
    private T data;

    public ApiResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(200, "Success", data);
    }

    public static <T> ApiResponse<T> failure(int code, String message) {
        return new ApiResponse<>(code, message, null);
    }

    public static ApiResponse<?> error(String message) {
        return new ApiResponse<>(500, message, null);
    }

    // getter 和 setter 方法


}
