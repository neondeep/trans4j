package com.fly.trans4j.core;

import cn.hutool.core.util.ReflectUtil;
import com.fly.trans4j.annotation.Trans;
import com.fly.trans4j.annotation.TransType;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 谢飞
 * @since 2023/6/10 9:01
 */
@Slf4j
public class TransMapper {

    private final Class<?> clazz;
    private final Map<TransType, List<Field>> transFieldMap = new HashMap<>();

    public TransMapper(Class<?> clazz) {
        this.clazz = clazz;
        Field[] fields = ReflectUtil.getFields(clazz);
        for (Field field : fields) {
            int mod = field.getModifiers();
            // 如果是 static, final, volatile, transient 的字段，则直接跳过
            if (Modifier.isStatic(mod) || Modifier.isFinal(mod) || Modifier.isVolatile(mod)) {
                continue;
            }
            Trans trans = field.getAnnotation(Trans.class);
            if (null != trans) {
                TransType transType = trans.type();
                List<Field> fieldList = transFieldMap.get(transType);
                fieldList = fieldList != null ? fieldList : new ArrayList<>();
                fieldList.add(field);
                transFieldMap.put(transType, fieldList);
            }
        }
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public Map<TransType, List<Field>> getTransFieldMap() {
        return transFieldMap;
    }
}
