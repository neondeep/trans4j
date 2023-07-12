package com.fly.trans4j.advice;

import com.fly.trans4j.annotation.IgnoreTrans;
import com.fly.trans4j.core.TransMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Executable;

/**
 * @author 谢飞
 * @since 2023/6/20 10:45
 */
@Slf4j
@ControllerAdvice
public class ResponseBodyAdviceTrans implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        IgnoreTrans classAnnotation = returnType.getContainingClass().getAnnotation(IgnoreTrans.class);
        if (null != classAnnotation) {
            return body;
        }
        IgnoreTrans methodAnnotation = returnType.getMethodAnnotation(IgnoreTrans.class);
        if (null != methodAnnotation) {
            return body;
        }
        return TransMapper.getInstance().trans(body);
    }
}
