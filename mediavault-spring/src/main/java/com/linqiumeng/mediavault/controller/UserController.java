package com.linqiumeng.mediavault.controller;

import com.linqiumeng.mediavault.entity.User;
import com.linqiumeng.mediavault.mapper.UserMapper;
import com.linqiumeng.mediavault.service.UserService;
import com.linqiumeng.mediavault.vo.Page;
import com.linqiumeng.mediavault.vo.UserQueryParams;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.Min;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    UserMapper userMapper;
    @Autowired
    private UserService userService;

    // 查询所有用户
    @GetMapping
    public List<User> getUser() {
        return userService.findAll();
    }

    // 根据Id查询用户
    @GetMapping("/{id}")
    public User findById(@PathVariable Integer id) {
        return userMapper.findById(id);
    }

    // 分页查询
    @GetMapping("/list")
    public Page<User> findByPage(@RequestParam(defaultValue = "1") @Min(1) Integer pageNum,
                                 @RequestParam(defaultValue = "10") @Min(1) Integer pageSize,
                                 @RequestParam(required = false) String id,
                                 @RequestParam(required = false) String name,
                                 @RequestParam(required = false) String phone) {


        UserQueryParams params = new UserQueryParams();
        params.setPageNum(pageNum);
        params.setPageSize(pageSize);

        // 处理可选参数
        params.setId(Optional.ofNullable(id).orElse(null));
        params.setName(Optional.ofNullable(name).orElse(null));
        params.setPhone(Optional.ofNullable(phone).orElse(null));

        Page<User> page = userService.findByPageAndConditions(params);
        return page;
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
        userMapper.updateById(user);
        return "success" + user.toString();
    }

    // 删除
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Integer id) {
        userMapper.deleteById(id);
        return "success";
    }

}
