package com.Derek.dpLuntan.async;

import java.util.List;

/**
 * Created by Administrator on 2017/6/20 0020.
 */
public interface EventHandler {
    void doHandle(EventModel model);

    List<EventType> getSupportEventTypes();
}
