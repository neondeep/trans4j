package com.fly.trans4j.trans;

import com.fly.trans4j.annotation.TransVO;
import com.fly.trans4j.util.TransHolder;

/**
 * @author 谢飞
 * @since 2023/6/12 11:48
 */
public abstract class AbstractTransService implements TransService {

    void proxySet(TransVO vo, String fieldName, Object fieldValue) {
        TransHolder.set(vo, fieldName, fieldValue);
    }

    void refSet() {

    }

}
