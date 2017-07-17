package com.Derek.dpLuntan.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by Administrator on 2017/6/19 0019.
 */
public class Demo {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("redis://localhost:6379/9");
        jedis.flushDB();
        jedis.sadd("demo", String.valueOf(1));
        //jedis.srem("demo", String.valueOf(1));
        System.out.println(jedis.srem("demo",String.valueOf(2)));
    }
}
