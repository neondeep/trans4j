package com.fly.trans4j.trans;

import com.fly.trans4j.annotation.TransType;
import com.fly.trans4j.annotation.TransVO;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author 谢飞
 * @since 2023/6/12 11:43
 */
public interface TransService {
    /**
     * @return 翻译类型
     */
    TransType getType();

    /**
     * 翻译
     *
     * @param vo             实现vo的实体
     * @param transFieldList 要翻译的字段
     * @throws Exception 抛出的异常
     */
    void trans(TransVO vo, List<Field> transFieldList) throws Exception;
}
