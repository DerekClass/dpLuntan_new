package com.Derek.dpLuntan.async.handler;

import com.Derek.dpLuntan.async.EventHandler;
import com.Derek.dpLuntan.async.EventModel;
import com.Derek.dpLuntan.async.EventType;
import com.Derek.dpLuntan.model.EntityType;
import com.Derek.dpLuntan.model.Feed;
import com.Derek.dpLuntan.model.Question;
import com.Derek.dpLuntan.model.User;
import com.Derek.dpLuntan.service.*;
import com.Derek.dpLuntan.util.JedisAdapter;
import com.Derek.dpLuntan.util.RedisKeyUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;


/**
 * Created by Administrator on 2017/6/27 0027.
 */
@Component
public class FeedHandler implements EventHandler {

    @Autowired
    QuestionService questionService;

    @Autowired
    FeedService feedService;

    @Autowired
    UserService userService;

    @Autowired
    FollowService followService;

    @Autowired
    JedisAdapter jedisAdapter;

    private String buildFeedData(EventModel model) {
        Map<String, String> map = new HashMap<>();
        User actor = userService.getUser(model.getActorId());
        if (actor == null) {
            return null;
        }
        map.put("userId", String.valueOf(actor.getId()));
        map.put("userHead", actor.getHeadUrl());
        map.put("userName", actor.getName());

        if (model.getType() == EventType.COMMENT ||
                (model.getType() == EventType.FOLLOW && model.getEntityType() == EntityType.ENTITY_QUESTION)) {
            Question question = questionService.selectById(model.getEntityId());
            if (question == null) {
                return null;
            }
            map.put("questionId", String.valueOf(question.getId()));
            map.put("questionTitle", question.getTitle());
            return JSONObject.toJSONString(map);
        }
        return null;
    }

    @Override
    public void doHandle(EventModel model) {
        // 方便测试数据
        Random r = new Random();
        model.setActorId(1 + r.nextInt(10));
        Feed feed = new Feed();
        feed.setCreatedDate(new Date());
        feed.setUserId(model.getActorId());
        feed.setType(model.getType().getValue());
        feed.setData(buildFeedData(model));
        if (feed.getData() == null) {
            return;
        }
        feedService.addFeed(feed);

        //给事件的粉丝推可以分段取（推模式）
        List<Integer> followers = followService.getFollowers(EntityType.ENTITY_USER, model.getActorId(), Integer.MAX_VALUE);
        followers.add(0);
        for (int follower : followers) {
            String timelineKey = RedisKeyUtil.getTimelineKey(follower);
            jedisAdapter.lpush(timelineKey, String.valueOf(feed.getId()));
        }


    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(new EventType[]{EventType.COMMENT, EventType.FOLLOW});
    }
}
