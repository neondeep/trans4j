package com.fly.trans4j.core;

import cn.hutool.core.util.ReflectUtil;
import com.fly.trans4j.annotation.Trans;
import com.fly.trans4j.annotation.TransType;
import com.fly.trans4j.annotation.TransVO;
import com.fly.trans4j.trans.TransService;
import com.fly.trans4j.util.ProxyUtil;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * @author 谢飞
 * @since 2023/6/10 9:01
 */
@Slf4j
public class TransMapper {
    private static final TransMapper INSTANCE = new TransMapper();

    private TransMapper() {
    }

    public static TransMapper getInstance() {
        return INSTANCE;
    }


    /**
     * 翻译
     *
     * @param object 要翻译的对象
     * @return 翻译后的对象
     */
    public Object trans(Object object) {
        try {
            if (null == object) {
                return null;
            }

            if (object instanceof TransVO) {// 对象翻译
                handlerTransVO((TransVO) object);
                // 看TransVO是否还有嵌套属性需要翻译
                transObjectFields(object);
            } else if (object instanceof Collection) {// 对象列表翻译
                return transObjectListFields(object);
            } else if (object.getClass().getName().startsWith("java.")) {
                return object;
            } else {
                // 如果不是TransVO，循环遍历看是否有字段类型属于TransVO
                transObjectFields(object);
            }
            TransConfiguration configuration = TransConfiguration.getInstance();
//            log.info("clazz name：{}", object.getClass().getName());
            return (configuration.getEnableProxy()) ? ProxyUtil.createProxyVo(object) : object;
        } catch (Exception e) {
            log.error("翻译失败", e);
            return object;
        }
    }

    /**
     * 翻译一个object的所有字段
     *
     * @param object 对象
     */
    public void transObjectFields(Object object) throws Exception {
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

    /**
     * 翻译一个List<object>的所有字段
     *
     * @param collection 对象列表
     * @return 翻译后的对象
     */
    public Object transObjectListFields(Object collection) throws Exception {
        Collection<?> objectList = (Collection<?>) collection;
        if (!(objectList.iterator().next() instanceof TransVO)) {
            return objectList;
        }
        Collection<Object> result;
        if (objectList instanceof List) {
            result = new ArrayList<>();
        } else if (objectList instanceof Set) {
            result = new HashSet<>();
        } else {
            return objectList;
        }
        for (Object object : objectList) {
            if (object instanceof TransVO) {
                TransVO vo = (TransVO) object;
                handlerTransVO(vo);
                result.add(ProxyUtil.createProxyVo(vo));
            } else {
                transObjectFields(object);
            }
        }
        return result;
    }

    /**
     * 翻译一个TransVO对象
     *
     * @param vo TransVO
     */
    private void handlerTransVO(TransVO vo) throws Exception {
        final Map<TransType, List<Field>> transFieldMap = getTransFieldMap(vo);

        for (Map.Entry<TransType, List<Field>> entry : transFieldMap.entrySet()) {
            TransType transType = entry.getKey();
            List<Field> transFieldList = entry.getValue();
            TransService transService = TransFactory.get(transType);
            transService.trans(vo, transFieldList);
        }
    }

    /**
     * 获取所有类型的翻译字段
     *
     * @param vo vo
     * @return 所有类型的翻译字段
     */

    private static Map<TransType, List<Field>> getTransFieldMap(TransVO vo) {
        final Map<TransType, List<Field>> transFieldMap = new HashMap<>();
        Field[] fields = ReflectUtil.getFields(vo.getClass());
        for (Field field : fields) {
            int mod = field.getModifiers();
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
        return transFieldMap;
    }

    /**
     * 检查对象是否存在指定字段
     *
     * @param object    对象
     * @param fieldName 字段名字
     * @return 是否存在
     */
    private boolean hasField(Object object, String fieldName) {
        try {
            object.getClass().getDeclaredField(fieldName);
            return true;
        } catch (NoSuchFieldException e) {
            return false;
        }
    }
}
