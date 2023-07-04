package com.fly.trans4j.util;

/**
 * @author 谢飞
 * @since 2023/6/29 16:57
 */
public class ObjectUtil {

    public static boolean isNull(Object obj) {
        return null == obj;
    }

    public static boolean isNotNull(Object obj) {
        return !isNull(obj);
    }
}
