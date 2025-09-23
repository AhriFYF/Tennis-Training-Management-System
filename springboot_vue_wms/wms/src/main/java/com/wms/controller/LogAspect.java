package com.wms.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.wms.common.Loggable;
import com.wms.entity.SystemLog;
import com.wms.service.SystemLogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@Aspect
@Component
@Slf4j
public class LogAspect {

    @Autowired
    private SystemLogService systemLogService;

    private static final ObjectMapper MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());

    @Pointcut("@annotation(com.wms.common.Loggable)")
    public void loggablePointcut() {}

    @Around("loggablePointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        SystemLog logEntity = new SystemLog();
        LocalDateTime actionTime = LocalDateTime.now();

        // 1. 获取请求上下文和用户信息
        Long currentUserId = getCurrentUserId();
        String ipAddress = null;
        String requestData = null;
        HttpServletRequest request = null;
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                request = attributes.getRequest();
                ipAddress = getIpAddress(request);
                requestData = MAPPER.writeValueAsString(joinPoint.getArgs());
                // 安全地打印 Session ID
                HttpSession session = request.getSession(false);
                log.info("Log Aspect Request Session ID: {}", (session != null ? session.getId() : "null"));
            }
        } catch (Exception e) {
            log.error("Failed to get request info or serialize request data.", e);
            requestData = "Failed to serialize request data.";
        }

        // 2. 获取方法签名和注解信息
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Loggable loggable = signature.getMethod().getAnnotation(Loggable.class);

        // 3. 设置日志实体
        logEntity.setUserId(currentUserId);
        System.out.println("logEntity得到的ID = " + currentUserId);
        logEntity.setActionType(loggable.actionType());
        logEntity.setActionDetail(loggable.actionDetail());
        logEntity.setActionTime(actionTime);
        logEntity.setIpAddress(ipAddress);
        logEntity.setRequestData(requestData);

        // 4. 执行目标方法
        Object result = joinPoint.proceed();

        // 5. 异步保存日志，确保在单独的线程中执行
        new Thread(() -> {
            try {
                systemLogService.save(logEntity);
                log.info("Successfully saved log for user ID: {}", currentUserId);
            } catch (Exception e) {
                log.error("Failed to save log asynchronously for user ID: {}", currentUserId, e);
            }
        }).start();

        return result;
    }

    private Long getCurrentUserId() {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                HttpSession session = attributes.getRequest().getSession(false);
                if (session != null) {
                    Object userIdObj = session.getAttribute("userId");
                    System.out.println("getCurrentUserId得到的ID = " + userIdObj);
                    if (userIdObj instanceof Integer) {
                        return ((Integer) userIdObj).longValue();
                    }
                    if (userIdObj instanceof Long) {
                        return (Long) userIdObj;
                    }
                }
            }
        } catch (Exception e) {
            log.error("Failed to get current user ID from session.", e);
        }
        return null;
    }

    private String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}