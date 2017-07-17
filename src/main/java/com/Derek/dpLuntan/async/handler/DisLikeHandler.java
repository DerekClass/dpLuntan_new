package com.Derek.dpLuntan.async.handler;

import com.Derek.dpLuntan.async.EventHandler;
import com.Derek.dpLuntan.async.EventModel;
import com.Derek.dpLuntan.async.EventType;
import com.Derek.dpLuntan.model.Message;
import com.Derek.dpLuntan.model.User;
import com.Derek.dpLuntan.service.MessageService;
import com.Derek.dpLuntan.service.UserService;
import com.Derek.dpLuntan.util.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/6/21 0021.
 */
@Component
public class DisLikeHandler implements EventHandler {
    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @Override
    public void doHandle(EventModel model) {
        Message message = new Message();
        message.setFromId(WendaUtil.SYSTEM_USERID);
        message.setCreatedDate(new Date());
        message.setToId(model.getEntityOwnerId());
        User user = userService.getUser(model.getActorId());
        message.setContent("用户" + user.getName() + "踩了你的评论，http://127.0.0.1:8080/question/" +
                model.getExt("questionId"));
        messageService.addMessage(message);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.DISLIKE);
    }
}
