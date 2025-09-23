package com.wms.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.wms.common.Loggable;
import com.wms.entity.SystemLog;
import com.wms.service.SystemLogService;
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
import java.time.LocalDateTime;

@Aspect
@Component
public class LogAspect {

    @Autowired
    private SystemLogService systemLogService;

    // 创建一个通用的Jackson ObjectMapper，用于将对象序列化为JSON字符串
    private static final ObjectMapper MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());

    /**
     * 定义一个切点，匹配所有带有 @Loggable 注解的方法
     */
    @Pointcut("@annotation(com.wms.common.Loggable)")
    public void loggablePointcut() {}

    /**
     * 环绕通知，在目标方法执行前后执行
     */
    @Around("loggablePointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        SystemLog log = new SystemLog();
        LocalDateTime actionTime = LocalDateTime.now();

        // 获取方法签名和注解信息
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Loggable loggable = signature.getMethod().getAnnotation(Loggable.class);

        // 设置日志的基本信息
        log.setActionType(loggable.actionType());
        log.setActionDetail(loggable.actionDetail());
        log.setActionTime(actionTime);

        // 获取请求信息，例如IP地址和请求参数
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                log.setIpAddress(getIpAddress(request));
                log.setRequestData(MAPPER.writeValueAsString(joinPoint.getArgs()));
            }
        } catch (Exception e) {
            System.err.println("Failed to get request info or serialize request data: " + e.getMessage());
            log.setRequestData("Failed to serialize request data.");
        }

        // ❗ 这里需要你实现获取当前登录用户ID的逻辑
        // 假设你有一个方法可以从会话或JWT中获取用户ID
        // 例如：
        Long currentUserId = getCurrentUserId();
        if (currentUserId != null) {
            log.setUser(currentUserId);
        }

        // 执行目标方法
        Object result = joinPoint.proceed();

        // 异步保存日志，不影响主业务流程
        new Thread(() -> {
            systemLogService.save(log);
        }).start();

        return result;
    }

    /**
     * 获取当前登录用户ID（此方法需要根据你的实际认证逻辑实现）
     */
    private Long getCurrentUserId() {
        // TODO: 从 session、JWT token 或 Spring Security上下文中获取当前用户ID
        // 示例：可以从会话中获取
        return (Long) ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession().getAttribute("userId");
    }

    /**
     * 获取请求IP地址
     */
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