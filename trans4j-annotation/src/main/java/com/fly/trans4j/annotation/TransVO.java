package com.fly.trans4j.annotation;

/**
 * @author 谢飞
 * @since 2023/6/11 14:35
 */
public interface TransVO {

    /**
     * 获取唯一值
     *
     * @return 唯一值
     */
    default Object transUnique() {
        return "";
    }

}
