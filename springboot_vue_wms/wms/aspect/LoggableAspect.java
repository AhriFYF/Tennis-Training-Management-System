package com.wms.aspect;

import com.wms.annotation.Loggable;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class LoggableAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggableAspect.class);

    @Around("@annotation(com.wms.annotation.Loggable)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Loggable loggable = method.getAnnotation(Loggable.class);

        String actionType = loggable.actionType();
        String actionDetail = loggable.actionDetail();

        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();

        logger.info("操作类型: {}, 操作详情: {}, 执行时间: {} ms", actionType, actionDetail, endTime - startTime);

        return result;
    }
}