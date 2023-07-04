package com.fly.trans4j.annotation;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author 谢飞
 * @since 2023/6/27 17:51
 */
public class TransHolder {

    private final static ThreadLocal<Map<String, Object>> HOLDER = new ThreadLocal<>();


    public static void set(String key, Object value) {
        Map<String, Object> transMap = Optional.ofNullable(get()).orElse(new HashMap<>());

        transMap.put(key, value);
        HOLDER.set(transMap);
    }

    public static Map<String, Object> get() {
        return HOLDER.get();
    }

    public static void remove() {
        HOLDER.remove();
    }
}
