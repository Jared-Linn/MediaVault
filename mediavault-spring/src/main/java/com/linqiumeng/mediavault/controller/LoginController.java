package com.linqiumeng.mediavault.controller;

import com.linqiumeng.mediavault.dto.ApiResponse;
import com.linqiumeng.mediavault.dto.token.ApiResponseToken;
import com.linqiumeng.mediavault.dto.LoginRequest;
import com.linqiumeng.mediavault.entity.User;
import com.linqiumeng.mediavault.service.UserServiceImpl;
import com.linqiumeng.mediavault.utils.jwt.JwtTokenProvider;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class LoginController {

    @Resource
    private UserServiceImpl userServiceImpl;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    /**
     * 登录方法
     *
     * @param loginRequest 登录请求对象
     * @return 登录是否成功
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponseToken<?>> login(@RequestBody LoginRequest loginRequest) {
        System.out.println("Username: " + loginRequest.getUsername());
        System.out.println("Password: " + loginRequest.getPassword());

        boolean loginSuccess = userServiceImpl.login(loginRequest.getUsername(), loginRequest.getPassword());

        if (loginSuccess) {
            User user = userServiceImpl.getUserByName(loginRequest.getUsername());
            String token = jwtTokenProvider.generateTokenWithUserId(user.getUsername(), Long.valueOf(user.getId()));
            return ResponseEntity.ok(ApiResponseToken.success(token));
        } else {
            return ResponseEntity.status(401).body(ApiResponseToken.failure(401, "Invalid credentials"));
        }
    }
}
