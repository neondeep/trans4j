package com.fly.trans4j.core.util;

/**
 * @author 谢飞
 * @since 2023/6/12 10:47
 */
public class Assert {

    public static void notNull(Object object, String errorMessage) throws IllegalArgumentException {
        if (null == object) {
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
