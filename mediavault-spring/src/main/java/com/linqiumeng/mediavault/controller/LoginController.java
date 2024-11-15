package com.linqiumeng.mediavault.controller;

import com.linqiumeng.mediavault.dto.ApiResponse;
import com.linqiumeng.mediavault.dto.ApiResponseToken;
import com.linqiumeng.mediavault.dto.LoginRequest;
import com.linqiumeng.mediavault.entity.User;
import com.linqiumeng.mediavault.service.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
//@CrossOrigin(origins = "http://172.10.10.2:80")
@RequestMapping()
public class LoginController {

    @Resource
    private UserService userService;
    @Value("${jwt.SECRET}")
    private static String SECRET ; // 替换为你的密钥

    /**
     * 登录方法
     *
     * @param loginRequest 登录请求对象
     * @return 登录是否成功
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponseToken<?>> login(@RequestBody LoginRequest loginRequest) {
        boolean loginSuccess = userService.login(loginRequest.getUsername(), loginRequest.getPassword());

        if (loginSuccess) {
            User user = userService.getUserByName(loginRequest.getUsername());
            String token = generateToken(user);
            return ResponseEntity.ok(ApiResponseToken.success(token));
        } else {
            return ResponseEntity.status(401).body(ApiResponseToken.failure(401, "Invalid credentials"));
        }
    }

    /**
     * 生成 Token
     *
     * @param user 用户对象
     * @return 生成的 Token
     */
    private String generateToken(User user) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        return JWT.create()
                .withSubject(user.getUsername())
                .withClaim("userId", user.getId())
                .withExpiresAt(new Date(System.currentTimeMillis() + 900000)) // 有效期 15 小时
                .sign(algorithm);
    }
}
