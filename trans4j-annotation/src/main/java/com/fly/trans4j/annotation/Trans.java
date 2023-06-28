package com.fly.trans4j.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 谢飞
 * @since 2023/6/9 11:00
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Trans {

    /**
     * 定义翻译类型
     */
    TransType type() default TransType.DICT;

    String key() default "";

    String ref() default "";
}
