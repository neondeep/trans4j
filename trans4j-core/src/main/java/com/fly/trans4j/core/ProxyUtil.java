package com.fly.trans4j.core;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ReflectUtil;
import com.fly.trans4j.annotation.TransVO;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.FixedValue;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.UUID;

/**
 * @author 谢飞
 * @since 2023/6/28 9:15
 */
public class ProxyUtil {


    public static Object createProxyVo(TransVO vo) {
        try {
            Map<String, Object> transMap = vo.getTransMap();
            if (transMap == null) {
                return vo;
            }

            DynamicType.Builder<?> builder = new ByteBuddy()
                .subclass(vo.getClass())
                .name(vo.getClass().getSimpleName() + "DynamicTypeBuilder" + IdUtil.fastSimpleUUID())
                .defineMethod("getTransMap", Map.class, Modifier.PUBLIC)
                .intercept(FixedValue.nullValue());

            for (String property : transMap.keySet()) {
                builder = builder.defineField(property, String.class, Modifier.PUBLIC);
            }

            Class<?> targetClass = builder.make()
                .load(ClassUtils.getDefaultClassLoader(), ClassLoadingStrategy.Default.INJECTION)
                .getLoaded();

            Object newObject = targetClass.newInstance();
            System.out.println(newObject);

            BeanUtils.copyProperties(vo, newObject);

            for (String property : vo.getTransMap().keySet()) {
                ReflectUtil.setFieldValue(newObject, property, transMap.get(property));
            }
            return newObject;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
