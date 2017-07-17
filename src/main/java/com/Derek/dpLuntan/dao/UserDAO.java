package com.Derek.dpLuntan.dao;

import com.Derek.dpLuntan.model.User;
import org.apache.ibatis.annotations.*;

/**
 * Created by Administrator on 2017/6/9 0009.
 */
@Mapper
public interface UserDAO {

    @Insert({"insert into user (name, password, salt, head_url) " +
            "values(#{name},#{password},#{salt},#{headUrl})"})
    int addUser(User user);


    @Select({"select id, name, password, salt, head_url from user where id=#{id}"})
    User selectById(int id);

    @Select({"select id, name, password, salt, head_url from user where name=#{name}"})
    User selectByName(String name);

    @Update({"update user set password =#{password} where id=#{id}"})
    void updatePassword(User user);

    @Delete({"delete from user where id=#{id}"})
    void deleteById(int id);
}
