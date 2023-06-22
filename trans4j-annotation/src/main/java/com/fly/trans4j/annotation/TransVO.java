package com.fly.trans4j.annotation;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author 谢飞
 * @since 2023/6/11 14:35
 */
public interface TransVO {
//    Map<String, String> transMap=new HashMap<>();
    default Map<String, String> getTransMap() {
        Map<String, String> result = new LinkedHashMap<>();
        return result;
    }
}
