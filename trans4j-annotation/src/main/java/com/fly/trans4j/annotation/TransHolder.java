package com.fly.trans4j.annotation;

import java.util.Map;

/**
 * @author 谢飞
 * @since 2023/6/27 17:51
 */
public class TransHolder {
    private final static ThreadLocal<Map<String, Object>> HOLDER = new ThreadLocal<>();


    public static void set(Map<String, Object> map) {
        HOLDER.set(map);
    }

    public static Map<String, Object> get() {
        return HOLDER.get();
    }

    public static void remove() {
        HOLDER.remove();
    }
}
