package com.fly.trans4j.annotation;

import java.util.Map;

/**
 * @author 谢飞
 * @since 2023/6/11 14:35
 */
public interface TransVO {
    default Map<String, Object> getTransMap() {
        return TransHolder.get();
    }
}
