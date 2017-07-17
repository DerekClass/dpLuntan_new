package com.Derek.dpLuntan.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/9 0009.
 */
public class ViewObject {
    private Map<String, Object> objs = new HashMap<>();

    public void set(String key, Object value) {
        objs.put(key, value);
    }

    public Object get(String key) {
        return objs.get(key);
    }
}
