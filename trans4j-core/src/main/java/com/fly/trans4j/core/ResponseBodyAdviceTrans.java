package com.fly.trans4j.core;

import com.fly.trans4j.annotation.Trans;
import com.fly.trans4j.annotation.TransVO;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import sun.reflect.FieldAccessor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author 谢飞
 * @since 2023/6/20 10:45
 */
@Slf4j
@ControllerAdvice
public class ResponseBodyAdviceTrans implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
//        log.info("ResponseBodyAdviceTrans supports：{}", returnType.getParameterType().isAssignableFrom(TransVO.class));
        log.info("parameterType：{}", returnType.getParameterType());
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        Object data = extractDataFromWrapper(body);
        if (data instanceof TransVO) {
            translateTransFields(data);
        }
        return body;
    }

    private Object extractDataFromWrapper(Object body) {
        if (body instanceof DataWrapper) {
            return ((DataWrapper) body).getTransData();
        }
        return body;
    }

    private void translateTransFields(Object body) {
        Class<?> bodyClass = body.getClass();
        Field[] fields = bodyClass.getDeclaredFields();

        Object proxyObj = dynamicProxy(bodyClass);


        for (Field field : fields) {
            if (field.isAnnotationPresent(Trans.class)) {
                translateField(proxyObj, field);
            }
        }
    }

    private void translateField(Object body, Field field) {
        try {
            field.setAccessible(true);
            Object value = field.get(body);
            if (value != null) {
                String translatedValue = translateValue(value);
                String fieldName = field.getName() + "Name";
                Field newField = body.getClass().getDeclaredField(fieldName);
                newField.setAccessible(true);
                newField.set(body, translatedValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String translateValue(Object value) {
        return "翻译的内容";
    }

    private static Object dynamicProxy(Object originalObject) {
        try {
            Class<?> originalClass = originalObject.getClass();
            // 使用 Byte Buddy 创建子类，并添加新字段
            DynamicType.Unloaded<?> unloadedType = new ByteBuddy()
                .subclass(originalClass)
                .name("DynamicObject")
                .defineField("sexName", String.class)
                .make();

            // 加载子类
            Class<?> dynamicClass = unloadedType.load(originalClass.getClassLoader(), ClassLoadingStrategy.Default.WRAPPER).getLoaded();

            // 创建子类对象
            Object dynamicObject = dynamicClass.newInstance();

            // 将原始对象的字段值复制到子类对象
            copyFields(originalObject, dynamicObject);

            // 设置新字段的值
            Field newField = dynamicClass.getDeclaredField("sexName");
            newField.setAccessible(true);
            newField.set(dynamicObject, "Hello, Dynamic Field!");

            return dynamicObject;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private static void copyFields(Object source, Object target) throws IllegalAccessException {
        Field[] fields = source.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Object value = field.get(source);
            field.set(target, value);
        }
    }
}
