package com.linqiumeng.mediavault.utils;

import lombok.Data;

/**
 * ，Result 类用于封装 API 返回的结果。通常，Result 类会包含一些通用的属性，如状态码、消息和数据。
 * @param <T>
 */
@Data
public class Result<T> {
    private int code; // 状态码
    private String message; // 消息
    private T data; // 数据

    // 成功的静态方法
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMessage("成功");
        result.setData(data);
        return result;
    }

    // 失败的静态方法
    public static <T> Result<T> error(String message) {
        Result<T> result = new Result<>();
        result.setCode(500);
        result.setMessage(message);
        return result;
    }
}
