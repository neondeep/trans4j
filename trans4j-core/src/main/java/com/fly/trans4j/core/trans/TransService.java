package com.fly.trans4j.core.trans;

import com.fly.trans4j.annotation.TransType;
import com.fly.trans4j.annotation.TransVO;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author 谢飞
 * @since 2023/6/12 11:43
 */
public interface TransService {
    TransType getType();

    void trans(TransVO vo, List<Field> transFieldList) throws Exception;
}
