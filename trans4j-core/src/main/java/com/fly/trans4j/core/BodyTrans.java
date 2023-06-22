package com.fly.trans4j.core;

import cn.hutool.core.util.ReflectUtil;
import com.fly.trans4j.annotation.TransType;
import com.fly.trans4j.annotation.TransVO;
import com.fly.trans4j.core.trans.TransService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;

/**
 * @author 谢飞
 * @since 2023/6/9 14:42
 */
@Slf4j
@ControllerAdvice
public class BodyTrans implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        try {
            return trans(body);
        } catch (Exception e) {
            log.error("翻译失败", e);
            return body;
        }
    }

    public Object trans(Object object) throws Exception {
        if (null == object) {
            return null;
        }
        log.info("类型是：{}", object.getClass().getName());
        if (object instanceof TransVO) {
            doTrans((TransVO) object);
        } else if (object.getClass().getName().startsWith("java.")) {
            return object;
        } else {
            transFields(object);
        }
        return object;
    }

    private void doTrans(TransVO object) throws Exception {
        log.info("doTrans");
        TransMapper mapper = new TransMapper(object.getClass());
        Map<TransType, List<Field>> transFieldMap = mapper.getTransFieldMap();
        for (Map.Entry<TransType, List<Field>> entry : transFieldMap.entrySet()) {
            TransType transType = entry.getKey();
            List<Field> transFieldList = entry.getValue();
            TransService transService = TransFactory.get(transType);
            transService.trans(object, transFieldList);
        }
    }

    public void transFields(Object object) throws Exception {
        Field[] fields = ReflectUtil.getFields(object.getClass());
        for (Field field : fields) {
            if (Modifier.isFinal(field.getModifiers()) || Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            field.setAccessible(true);
            Object fieldValue = field.get(object);
            field.set(object, trans(fieldValue));
        }
    }
}
