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

    /**
     * 关键字
     * 字典-表示字典类型
     * 枚举-表示枚举里面要显示的哪个字段
     */
    String key() default "";

    /**
     * 引用字段，翻译的值最终会映射到该ref的字段，注意该字段必须存在于实体
     */
    String ref() default "";

    /**
     * 当不指定ref时，代理模式字段后缀
     */
    String suffix() default "Name";
}
