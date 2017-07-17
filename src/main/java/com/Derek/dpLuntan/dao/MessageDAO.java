package com.Derek.dpLuntan.dao;

import com.Derek.dpLuntan.model.Message;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;
import sun.plugin2.message.Conversation;

import java.util.List;

/**
 * Created by Administrator on 2017/6/16 0016.
 */
@Mapper
public interface MessageDAO {

    @Update({"update message set has_read ='1' where conversation_id=#{conversationId} and to_id=#{userId}"})
    int updateHasread(@Param("conversationId") String conversationId,
                      @Param("userId") int userId);

    @Insert({"insert into message(from_id, to_id, content, has_read, conversation_id, created_date) " +
            "values (#{fromId},#{toId},#{content},#{hasRead},#{conversationId},#{createdDate})"})
    int addMessage(Message message);

    @Select({"select id, from_id, to_id, content, has_read, conversation_id, created_date from message" +
            " where conversation_id=#{conversationId} order by created_date desc limit #{offset}, #{limit}"})
    List<Message> getConversationDetail(@Param("conversationId") String conversationId,
                                        @Param("offset") int offset,
                                        @Param("limit") int limit);

    @Select({"select  from_id, to_id, content, has_read, conversation_id, created_date, count(id) as id from(select * from" +
            " message where from_id=#{userId} or to_id=#{userId} order by created_date desc) tt group by conversation_id" +
            " order by created_date desc limit #{offset}, #{limit}"})
    List<Message> getConversationList(@Param("userId") int userId,
                                      @Param("offset") int offset,
                                      @Param("limit") int limit);

    @Select({"select count(id) from message where has_read= 0 and to_id=#{userId} and conversation_id = #{conversationId}"})
    int getConversationUnreadCount(@Param("userId") int userId,
                                   @Param("conversationId") String conversationId);

   /* @Select({"select has_read from message where conversation_id=#{conversationId} and to_id=#{userId}"})
    List<Integer> getHasRead(@Param("userId") int userId,
                            @Param("conversationId") String conversationId);*/
}
