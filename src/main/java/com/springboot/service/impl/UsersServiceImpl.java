package com.springboot.service.impl;

import com.springboot.mapper.UsersMapper;
import com.springboot.model.Users;
import com.springboot.service.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Vincent on 2017/3/16.
 */
@Service("usersService")
public class UsersServiceImpl implements UsersService{
    private static final Logger logger = LoggerFactory.getLogger(UsersService.class);

    @Resource(name = "usersMapper")
    private UsersMapper usersMapper;

    /**
     * 根据主键查询用户
     *
     * @param uid
     */
    @Override
    public Users selectByPrimaryKey(int uid) {
        return usersMapper.selectByPrimaryKey(uid);
    }
}
