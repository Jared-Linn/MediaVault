package com.linqiumeng.mediavault.service.User;

import com.linqiumeng.mediavault.entity.User;
import com.linqiumeng.mediavault.mapper.UserMapper;
import com.linqiumeng.mediavault.vo.Page;
import com.linqiumeng.mediavault.service.User.impl.UserService;
import com.linqiumeng.mediavault.vo.UserQueryParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserMapper userMapper;


    // 分页查询 - 根据分页查询的数据 进行 模糊查询
    public Page<User> findByPageAndConditions(UserQueryParams params) {
        // 计算分页查询的偏移量
        int offset = (params.getPageNum() - 1) * params.getPageSize();

        params.setOffset(offset);

        try {
            // 查询分页数据
            List<User> data = userMapper.findByPageAndConditions(params, offset, params.getPageSize());

            // 获取总记录数
            int total = userMapper.getTotalUserCountByConditions(params);

            // 创建 Page 对象并设置属性
            return new Page<>(params.getPageNum(), params.getPageSize(), total, data);


        } catch (Exception e) {
            // 处理异常，返回空结果或抛出自定义异常
            e.printStackTrace();
            return new Page<>(params.getPageNum(), params.getPageSize(), 0, null);
        }
    }


    /**
     * 登录方法
     *
     * @param username 用户名
     * @param password 密码
     * @return 登录是否成功
     */
    public boolean login(String username, String password) {
        try {
            User user = userMapper.findByUsername(username);
            if (user != null) {
                logger.info("User logged in successfully: {}", username);
                return true;
            } else {
                logger.warn("Login failed for user: {}", username);
                return false;
            }
        } catch (Exception e) {
            logger.error("Error during login for user: {}", username, e);
            return false;
        }
    }

    public List<User> findAll() {
        return userMapper.findAll();
    }


    @Override
    public User getUserByName(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    public User getUserById(Integer id) {
        return userMapper.findById(id);
    }


    @Override
    public User getUserByNameAndId(String username , Integer id) {
        return userMapper.getUserByNameAndId(username, id);
    }

    @Override
    public Long getIdByUserName(String username) {
        return userMapper.getIdByUserName(username);
    }


    public void save(User user) {
        userMapper.save(user);
    }
}
