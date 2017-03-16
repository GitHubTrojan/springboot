package com.springboot.controller;

import com.springboot.model.Users;
import com.springboot.service.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by Vincent on 2017/3/16.
 */
@RestController
@RequestMapping(value = "/users")
public class UsersController {
    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);

    @Resource(name = "usersService")
    private UsersService usersService;

    @RequestMapping(value = "/testMybatis")
    @ResponseBody
    public Object testMybatis(){
        logger.info("test begins...");
        Users users = usersService.selectByPrimaryKey(1);
        logger.info(users == null ? "" : users.toString());
        logger.info("test end...");
        return users;
    }
}
