package com.fly.trans4j.trans;

import cn.hutool.core.util.ReflectUtil;
import com.fly.trans4j.annotation.Trans;
import com.fly.trans4j.annotation.TransType;
import com.fly.trans4j.annotation.TransVO;
import com.fly.trans4j.core.TransFactory;
import com.fly.trans4j.util.Assert;
import org.springframework.beans.factory.InitializingBean;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author 谢飞
 * @since 2023/7/4 14:05
 */
public class EnumTransService extends AbstractTransService implements InitializingBean {
    @Override
    public TransType getType() {
        return TransType.ENUM;
    }

    @Override
    public void trans(TransVO vo, List<Field> transFieldList) throws Exception {
        for (Field field : transFieldList) {
            Trans trans = field.getAnnotation(Trans.class);
            if (null == trans) {
                continue;
            }
            Assert.notNull(trans.key(), "enum翻译时Trans注解的key不能为空");
            field.setAccessible(true);
            Object fieldValue = field.get(vo);
            if (null == fieldValue) {
                continue;
            }
            Class<?> clazz = fieldValue.getClass();

            if (clazz.isEnum()) {
                String key = trans.key();
                String[] refs = trans.refs();
                String suffix = trans.suffix();
                Field enumField = ReflectUtil.getField(clazz, key);
                enumField.setAccessible(true);
                Object transValue = enumField.get(fieldValue);
                if (refs.length > 0) {
                    for (String ref : refs) {
                        ReflectUtil.setFieldValue(vo, ref, transValue);
                    }
                } else {
                    proxySet(vo, field.getName() + suffix, transValue);
                }
            }
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        TransFactory.register(getType(), this);
    }
}
