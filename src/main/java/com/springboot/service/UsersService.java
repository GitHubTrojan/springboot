package com.springboot.service;

import com.springboot.model.Users;

/**
 * Created by Vincent on 2017/3/16.
 */
public interface UsersService {
    /** 根据主键查询用户 */
    Users selectByPrimaryKey(int uid);
}
