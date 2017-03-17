package com.springboot.base;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Vincent on 2017/3/17.
 */
@Service
public interface BaseService<T> {

    /** insert one record / 插入一条记录 */
    int insertByPrimaryKey(T record);

    /** insert one record / 插入一条具有给定内容的记录 */
    int insertByPrimaryKeySelective(T record);

    /** batch insert / 批量插入多条记录 */
    int batchInsert(List<T> records);

    /** delete one record / 用主键删除一条记录 */
    int deleteByPrimaryKey(int id);

    /** update one record / 用主键更新一条记录 */
    int updateByPrimaryKey(T record);

    /** update one record / 用主键更新一条记录的指定内容 */
    int updateByPrimaryKeySelective(T record);

    /** update one/more record / 用主键更新一条/多条记录 */
    int batchUpdateByPK(List<T> records);

    /** select one record by primary key / 根据主键获取一条记录*/
    T selectByPrimaryKey(int id);

    /** query records / 查询表中所有记录 */
    List<T> queryForList();

    /** query records / 查询表中所有记录 */
    List<T> queryForList(T item);

    /** clear all records by conditions  /  删除这些记录、对象*/
    int clearThese(List<T> records);

    /** count all records in this table  / 查询表获取当前记录数*/
    int count();

}
