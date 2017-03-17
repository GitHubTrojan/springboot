package com.springboot.controller;

import com.springboot.base.BaseController;
import com.springboot.base.BaseService;
import com.springboot.model.Users;
import com.springboot.service.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Vincent on 2017/3/16.
 */
@RestController
@RequestMapping(value = "/users")
public class UsersController extends BaseController<Users> {
//    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);

    @Resource(name = "usersService")
    private UsersService usersService;

    @RequestMapping(value = "/testMybatis")
    @ResponseBody
    public Object testMybatis(@RequestParam("token") String token, HttpServletRequest request){
        logger.info("test begins...");
        System.out.println("token : " + token);
        Users users = usersService.selectByPrimaryKey(1);
        logger.info(users == null ? "" : users.toString());
        users.setUname("IntelliJ Spring Boot");
//        int result = usersService.insertByPrimaryKey(users);
//        logger.info("插入操作的执行结果：" + result);
//        List<Users> usersList = usersService.queryForList();
//        logger.info("usersList: " + usersList == null ? "列表空" : usersList.toString());
        users = usersService.selectByPrimaryKey(44);
        logger.info(users == null ? "" : users.toString());
        logger.info("test end...");
        return users;
    }
}
