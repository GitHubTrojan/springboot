package com.springboot.mapper;

import com.springboot.model.Users;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Vincent on 2017/3/16.
 */
@Repository
public interface UsersMapper {

    @Select("select uid, uname from users where uid = #{uid}")
    Users selectByPrimaryKey(int uid);

    int insertSelective(Users user);

    @Select("select uid, uname from users")
    List<Users> selectAll();

    @Select("select uid, uname from users where uname = #{uname}")
    List<Users> queryForList(Users users);
}
