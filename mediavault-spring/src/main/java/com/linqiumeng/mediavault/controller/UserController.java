package com.linqiumeng.mediavault.controller;

import com.linqiumeng.mediavault.dto.ApiResponse;
import com.linqiumeng.mediavault.dto.token.ApiResponseToken;
import com.linqiumeng.mediavault.entity.User;
import com.linqiumeng.mediavault.exception.ResourceNotFoundException;
import com.linqiumeng.mediavault.mapper.UserMapper;
import com.linqiumeng.mediavault.service.UserServiceImpl;
import com.linqiumeng.mediavault.vo.Page;
import com.linqiumeng.mediavault.vo.UserQueryParams;

import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/sys/user")
public class UserController {
    @Resource
    UserMapper userMapper;
    @Autowired
    private UserServiceImpl userServiceImpl;

    // 查询所有用户
    @GetMapping
    public List<User> getUser() {
        return userServiceImpl.findAll();
    }

    // 根据Id查询用户
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> findById(@PathVariable Integer id) {
        User user = userMapper.findById(id);
        if (user == null) {
            throw new ResourceNotFoundException("用户未找到，ID: " + id);
        }
        return ResponseEntity.ok(ApiResponse.success(user));
    }

    // 分页查询
    @GetMapping("/list")
    public ResponseEntity<ApiResponse<?>> findByPage(@RequestParam(defaultValue = "1") @Min(1) Integer pageNum,
                                 @RequestParam(defaultValue = "10") @Min(1) Integer pageSize,
                                 @RequestParam(required = false) String id,
                                 @RequestParam(required = false) String username,
                                 @RequestParam(required = false) String phone) {

        UserQueryParams params = new UserQueryParams();
        params.setPageNum(pageNum);
        params.setPageSize(pageSize);

        // 处理可选参数
        params.setId(Optional.ofNullable(id).orElse(null));
        params.setUsername(Optional.ofNullable(username).orElse(null));
        params.setPhone(Optional.ofNullable(phone).orElse(null));

        Page<User> page = userServiceImpl.findByPageAndConditions(params);
        return ResponseEntity.ok(ApiResponse.success(page));

    }

    // 新增一个用户
    @PostMapping
    public String addUser(@RequestBody User user) {
        userMapper.save(user);
        return "success" + user.toString();
    }

    // 修改
    @PutMapping
    public String updateUser(@RequestBody User user) {
        if (userMapper.updateById(user) == 0) {
            throw new ResourceNotFoundException("用户未找到，ID: " + user.getId());
        }
        return "success" + user.toString();
    }

    // 删除
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Integer id) {
        if (userMapper.deleteById(id) == 0) {
            throw new ResourceNotFoundException("用户未找到，ID: " + id);
        }
        return "success";
    }
}
