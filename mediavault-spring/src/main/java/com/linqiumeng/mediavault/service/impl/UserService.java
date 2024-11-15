package com.linqiumeng.mediavault.service.impl;

import com.linqiumeng.mediavault.entity.User;

public interface UserService {
    boolean login(String username, String password);
    User getUserByName(String username);

}
