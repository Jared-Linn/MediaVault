package com.linqiumeng.mediavault.service;

import ch.qos.logback.core.util.StringUtil;
import org.springframework.stereotype.Service;

/**
 *  权限服务
 *
 *  @author lqm
 */
@Service("ss")
public class PermissionService {
    /**
     * 所有权限标识
     */
    private static final String ALL_PERMISSION = "*:*:*";


    /**
     * 管理员角色权限标识
     */
    private static final String SUPER_ADMIN = "admin";

    private static final String ROLE_DELIMETER = ",";

    private static final String PERMISSION_DELIMETER = ",";

    /**
     * 验证 用户是否具备某权限
     *
     * @author lqm
     */
    public boolean hasPermission(String permission) {
        if (StringUtil.isNullOrEmpty(permission)){
            return false;
        }
        return true;
    }


}
