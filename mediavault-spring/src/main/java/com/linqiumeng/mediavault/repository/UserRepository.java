package com.linqiumeng.mediavault.repository;

import com.linqiumeng.mediavault.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
/*
     用户仓库
     @author linqiumeng
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
