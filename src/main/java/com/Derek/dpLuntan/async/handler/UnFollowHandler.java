package com.Derek.dpLuntan.async.handler;

import com.Derek.dpLuntan.async.EventHandler;
import com.Derek.dpLuntan.async.EventModel;
import com.Derek.dpLuntan.async.EventType;
import com.Derek.dpLuntan.model.EntityType;
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
 * Created by Administrator on 2017/6/20 0020.
 */
@Component
public class UnFollowHandler implements EventHandler {
    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @Override
    public void doHandle(EventModel model) {
        Message message = new Message();
        message.setFromId(WendaUtil.SYSTEM_USERID);
        message.setToId(model.getEntityOwnerId());
        message.setCreatedDate(new Date());
        User user = userService.getUser(model.getActorId());
        if (model.getEntityType() == EntityType.ENTITY_QUESTION) {
            message.setContent("用户" + user.getName() + "取消关注了你的问题，http://127.0.0.1:8080/question/" + model.getEntityId());
            messageService.addMessage(message);
        } else if (model.getEntityType() == EntityType.ENTITY_USER) {
            message.setContent("用户" + user.getName() + "取消关注了你，http://127.0.0.1:8080/user/" + model.getActorId());
            messageService.addMessage(message);
        }
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.UNFOLLOW);
    }
}
