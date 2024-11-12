package com.linqiumeng.mediavault.exception;

import com.linqiumeng.mediavault.dto.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<UserResponse> handleException(Exception ex) {
        UserResponse response = new UserResponse(null, null, "系统内部错误: " + ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<UserResponse> handleNullPointerException(NullPointerException ex) {
        UserResponse response = new UserResponse(null, null, "空指针异常: " + ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<UserResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        UserResponse response = new UserResponse(null, null, "非法参数异常: " + ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
