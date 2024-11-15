package com.linqiumeng.mediavault.controller;

import com.linqiumeng.mediavault.dto.ApiResponse;
import com.linqiumeng.mediavault.entity.User;
import com.linqiumeng.mediavault.exception.ResourceNotFoundException;
import com.linqiumeng.mediavault.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TokenValidationController {
    @Resource
    UserMapper userMapper;
    /**
     * 验证JWT的有效性
     *
     * @return 返回JWT是否有效的响应
     */
    @GetMapping("/validate-token")
    public ResponseEntity<String> validateToken() {
        return ResponseEntity.ok("Token is valid");
    }


    @GetMapping("/user/{id}")
    public ResponseEntity<ApiResponse<?>> findById(@PathVariable Integer id) {
        User user = userMapper.findById(id);
        if (user == null) {
            throw new ResourceNotFoundException("用户未找到，ID: " + id);
        }
        return ResponseEntity.ok(ApiResponse.success(user));
    }
}
