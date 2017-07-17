package com.Derek.dpLuntan.async.handler;

import com.Derek.dpLuntan.async.EventHandler;
import com.Derek.dpLuntan.async.EventModel;
import com.Derek.dpLuntan.async.EventType;
import com.Derek.dpLuntan.model.Message;
import com.Derek.dpLuntan.model.User;
import com.Derek.dpLuntan.service.MessageService;
import com.Derek.dpLuntan.service.UserService;
import com.Derek.dpLuntan.util.RedisKeyUtil;
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
public class LoginHandler implements EventHandler {
    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @Override
    public void doHandle(EventModel model) {
        Message message = new Message();
        message.setFromId(WendaUtil.SYSTEM_USERID);
        message.setToId(model.getActorId());
        User user = userService.getUser(model.getActorId());
        message.setCreatedDate(new Date());
        message.setContent("您好" + user.getName() + ", 欢迎登录DP论坛");
        messageService.addMessage(message);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LOGIN);
    }
}
