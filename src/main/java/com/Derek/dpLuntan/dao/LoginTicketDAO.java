package com.Derek.dpLuntan.dao;

import com.Derek.dpLuntan.model.LoginTicket;
import org.apache.ibatis.annotations.*;

/**
 * Created by Administrator on 2017/6/13 0013.
 */
@Mapper
public interface LoginTicketDAO {

    @Insert({"insert into login_ticket(user_id, expired, status, ticket) " +
            "values(#{userId},#{expired},#{status},#{ticket})"})
    int addTicket(LoginTicket ticket);

    @Select({"select id, user_id, ticket, expired, status from login_ticket where ticket=#{ticket}"})
    LoginTicket selectByTicket(String ticket);

    @Update({"update login_ticket set status=#{status} where ticket=#{ticket}"})
    void updateStatus(@Param("ticket") String ticket, @Param("status") int status);
}
