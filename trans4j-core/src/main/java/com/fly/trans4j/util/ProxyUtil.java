package com.fly.trans4j.util;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ReflectUtil;
import com.fly.trans4j.annotation.TransVO;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 谢飞
 * @since 2023/6/28 9:15
 */
public class ProxyUtil {

    private static final Map<Class<?>, Class<?>> proxyClassMap = new ConcurrentHashMap<>();

    public static Object createProxyVo(Object object) {
        try {
            if (!(object instanceof TransVO)) {
                return object;
            }

            TransVO vo = (TransVO) object;
            Map<String, Object> transMap = TransHolder.get(vo);
            if (transMap == null) {
                return vo;
            }
            Class<?> targetClass = genNewClass(vo);
            Object newObject = targetClass.newInstance();

            BeanUtils.copyProperties(vo, newObject);

            for (String fieldName : transMap.keySet()) {
                ReflectUtil.setFieldValue(newObject, fieldName, transMap.get(fieldName));
            }
            return newObject;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Class<?> genNewClass(TransVO vo) {
        Map<String, Object> transMap = TransHolder.get(vo);
        Class<?> targetClass = proxyClassMap.get(vo.getClass());
        if (null != targetClass) {
            return targetClass;
        }

        DynamicType.Builder<?> builder = new ByteBuddy()
            .subclass(vo.getClass())
            .name(vo.getClass().getSimpleName() + "DynamicTypeBuilder" + IdUtil.fastSimpleUUID())
//            .defineMethod("getTransMap", Map.class, Modifier.PUBLIC).intercept(FixedValue.nullValue())
            ;

        for (String fieldName : transMap.keySet()) {
            builder = builder.defineField(fieldName, String.class, Modifier.PUBLIC);
        }

        targetClass = builder.make()
            .load(ClassUtils.getDefaultClassLoader(), ClassLoadingStrategy.Default.INJECTION)
            .getLoaded();

        proxyClassMap.put(vo.getClass(), targetClass);
        return targetClass;
    }
}
