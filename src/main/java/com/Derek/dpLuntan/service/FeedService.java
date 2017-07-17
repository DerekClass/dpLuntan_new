package com.Derek.dpLuntan.service;

import com.Derek.dpLuntan.dao.FeedDAO;
import com.Derek.dpLuntan.model.Feed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/6/27 0027.
 */
@Service
public class FeedService {
    @Autowired
    FeedDAO feedDAO;

    //拉模式
    public List<Feed> getUserFeeds(int maxId, List<Integer> userIds, int count) {
        return feedDAO.selectUserFeeds(maxId, userIds, count);
    }

    public boolean addFeed(Feed feed) {
        feedDAO.addFeed(feed);
        return feed.getId() > 0;
    }

    //推模式
    public Feed getById(int id) {
        return feedDAO.getFeedById(id);
    }
}
