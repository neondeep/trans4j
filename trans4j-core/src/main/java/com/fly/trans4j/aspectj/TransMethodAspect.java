package com.fly.trans4j.aspectj;

import com.fly.trans4j.core.TransMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author 谢飞
 * @since 2023/6/29 13:45
 */
@Slf4j
@Aspect
public class TransMethodAspect {

    @Pointcut("@annotation(com.fly.trans4j.annotation.TransMethod)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object object = joinPoint.proceed();
        return TransMapper.getInstance().trans(object);
    }
}
