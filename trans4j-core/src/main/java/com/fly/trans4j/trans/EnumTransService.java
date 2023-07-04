package com.fly.trans4j.trans;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.fly.trans4j.annotation.Trans;
import com.fly.trans4j.annotation.TransHolder;
import com.fly.trans4j.annotation.TransType;
import com.fly.trans4j.annotation.TransVO;
import com.fly.trans4j.core.TransFactory;
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
            field.setAccessible(true);
            Object fieldValue = field.get(vo);
            if (null == fieldValue) {
                continue;
            }
            Class<?> clazz = fieldValue.getClass();

            if (clazz.isEnum()) {
                String key = trans.key();
                String ref = trans.ref();
                String suffix = trans.suffix();
                Field enumField = ReflectUtil.getField(clazz, key);
                enumField.setAccessible(true);
                Object transValue = enumField.get(fieldValue);
                if (StrUtil.isNotBlank(ref)) {
                    ReflectUtil.setFieldValue(vo, ref, transValue);
                } else {
                    TransHolder.set(field.getName() + suffix, transValue);
                }
            }
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        TransFactory.register(getType(), this);
    }
}
