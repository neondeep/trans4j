package com.fly.trans4j.core.trans;

import com.fly.trans4j.annotation.TransType;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author 谢飞
 * @since 2023/6/12 11:48
 */
public abstract class AbstractTransService implements TransService {

    private static Map<TransType, TransService> transServiceMap = new LinkedHashMap<>();

    public static void register(TransType transType, TransService transService) {
        transServiceMap.put(transType, transService);
    }

    public static TransService get(TransType transType) {
        return transServiceMap.get(transType);
    }

}
