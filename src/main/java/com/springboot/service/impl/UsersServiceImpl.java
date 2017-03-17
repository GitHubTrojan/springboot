package com.springboot.service.impl;

import com.springboot.mapper.UsersMapper;
import com.springboot.model.Users;
import com.springboot.service.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Vincent on 2017/3/16.
 * @version 1.0.0
 */
@Service("usersService")
public class UsersServiceImpl implements UsersService{
    private static final Logger logger = LoggerFactory.getLogger(UsersService.class);

    @Resource(name = "usersMapper")
    private UsersMapper usersMapper;

    /**
     * 插入用户
     * @param record
     */
    @Override
    public int insertByPrimaryKey(Users record) {
        return usersMapper.insertSelective(record);
    }

    /**
     * insert one record / 插入一条具有给定内容的记录
     *
     * @param record
     */
    @Override
    public int insertByPrimaryKeySelective(Users record) {
        return 0;
    }

    /**
     * batch insert / 批量插入多条记录
     *
     * @param records
     */
    @Override
    public int batchInsert(List<Users> records) {
        return 0;
    }

    /**
     * delete one record / 用主键删除一条记录
     *
     * @param id
     */
    @Override
    public int deleteByPrimaryKey(int id) {
        return 0;
    }

    /**
     * update one record / 用主键更新一条记录
     *
     * @param record
     */
    @Override
    public int updateByPrimaryKey(Users record) {
        return 0;
    }

    /**
     * update one record / 用主键更新一条记录的指定内容
     *
     * @param record
     */
    @Override
    public int updateByPrimaryKeySelective(Users record) {
        return 0;
    }

    /**
     * update one/more record / 用主键更新一条/多条记录
     *
     * @param records
     */
    @Override
    public int batchUpdateByPK(List<Users> records) {
        return 0;
    }

    /**
     * select one record by primary key / 根据主键获取一条记录
     *
     * @param uid
     */
    @Override
    public Users selectByPrimaryKey(int uid) {
        return usersMapper.selectByPrimaryKey(uid);
    }

    /**
     * query records / 查询表中所有记录

     */
    @Override
    public List<Users> queryForList() {
        return usersMapper.selectAll();
    }

    /**
     * query records / 查询表中所有记录
     *
     * @param item
     */
    @Override
    public List<Users> queryForList(Users users) {
        return usersMapper.queryForList(users);
    }

    /**
     * clear all records by conditions  /  删除这些记录、对象
     *
     * @param records
     */
    @Override
    public int clearThese(List<Users> records) {
        return 0;
    }

    /**
     * count all records in this table  / 查询表获取当前记录数
     */
    @Override
    public int count() {
        return 0;
    }
}
