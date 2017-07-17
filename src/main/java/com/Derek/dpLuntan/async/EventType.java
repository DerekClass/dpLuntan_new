package com.Derek.dpLuntan.async;

/**
 * Created by Administrator on 2017/6/20 0020.
 */
public enum EventType {
    LIKE(0),
    COMMENT(1),
    LOGIN(2),
    MAIL(3),
    DISLIKE(4),
    REGISTERED(5),
    FOLLOW(6),
    UNFOLLOW(7);

    private int value;

    EventType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
