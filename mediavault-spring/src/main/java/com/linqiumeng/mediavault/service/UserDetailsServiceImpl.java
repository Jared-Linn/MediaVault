package com.linqiumeng.mediavault.service;

import com.linqiumeng.mediavault.entity.User;
import com.linqiumeng.mediavault.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * @author linqiumeng
 * 用户详细信息服务
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Override
    public UserDetails loadUserByUsername(String username ) throws UsernameNotFoundException {
        logger.info("Loading user by username: {}", username);
        // 根据用户名查询用户
        User user = userServiceImpl.getUserByName(username);
        logger.info("Loading user  : {},{}", user.getId(), user.getUsername());



        if (user == null) {
            logger.error("User not found for username: {}", username);
            throw new UsernameNotFoundException("User not found");

        }

        // 返回 UserDetails 对象
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                true,
                true,
                true,
                true,
                new ArrayList<>());
    }

    // 检测 id + username 是否存在
    public User checkUser(String username, Integer id) {
        return userServiceImpl.getUserByNameAndId(username,id);
    }

    // 检测用户名是否重复

    // 检测手机号是否重复

    // 检测用户名是否重复

}
