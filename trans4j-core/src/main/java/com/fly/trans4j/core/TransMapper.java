package com.fly.trans4j.core;

import cn.hutool.core.util.ReflectUtil;
import com.fly.trans4j.annotation.Trans;
import com.fly.trans4j.annotation.TransType;
import com.fly.trans4j.annotation.TransVO;
import com.fly.trans4j.core.trans.TransService;
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
    private static final TransMapper INSTANCE = new TransMapper();

    private final static boolean isProxy = true;

    private TransMapper() {
    }

    public static TransMapper getInstance() {
        return INSTANCE;
    }


    public Object trans(Object object) throws Exception {
        if (null == object) {
            return null;
        }
        if (object instanceof TransVO) {
            handlerTransVO((TransVO) object);
        } else if (object.getClass().getName().startsWith("java.")) {
            return object;
        } else {
            // 如果不是TransVO，循环遍历看是否有字典类型属于TransVO
            transFields(object);
        }
        return (isProxy && object instanceof TransVO) ? ProxyUtil.createProxyVo((TransVO) object) : object;
    }

    /**
     * 翻译一个object的子属性
     *
     * @param object object
     */
    public void transFields(Object object) throws Exception {
        // 获取一个类的所有字段，包括父类
        Field[] fields = ReflectUtil.getFields(object.getClass());
        for (Field field : fields) {
            // 排除不翻译的字段
            if (Modifier.isFinal(field.getModifiers()) || Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            field.setAccessible(true);
            Object fieldValue = field.get(object);
            field.set(object, trans(fieldValue));
        }
    }

    private void handlerTransVO(TransVO vo) throws Exception {
        final Map<TransType, List<Field>> transFieldMap = new HashMap<>();
        Field[] fields = ReflectUtil.getFields(vo.getClass());
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


        for (Map.Entry<TransType, List<Field>> entry : transFieldMap.entrySet()) {
            TransType transType = entry.getKey();
            List<Field> transFieldList = entry.getValue();
            TransService transService = TransFactory.get(transType);
            transService.trans(vo, transFieldList);
        }
    }

    // 检查对象是否存在指定字段
    private boolean hasField(Object object, String fieldName) {
        try {
            object.getClass().getDeclaredField(fieldName);
            return true;
        } catch (NoSuchFieldException e) {
            return false;
        }
    }
}
