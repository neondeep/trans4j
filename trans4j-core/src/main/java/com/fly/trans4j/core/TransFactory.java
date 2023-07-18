package com.fly.trans4j.core;

import com.fly.trans4j.annotation.TransType;
import com.fly.trans4j.exception.TransException;
import com.fly.trans4j.trans.TransService;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author 谢飞
 * @since 2023/6/12 13:52
 */
public class TransFactory {

    private static final Map<TransType, TransService> transServiceMap = new LinkedHashMap<>();

    public static void register(TransType transType, TransService transService) {
        transServiceMap.put(transType, transService);
    }

    public static TransService get(TransType transType) {
        TransService transService = transServiceMap.get(transType);
        if (null == transService) {
            throw new TransException("翻译类型未找到实现类");
        }
        return transService;
    }
}
