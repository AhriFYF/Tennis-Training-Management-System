package com.wms.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义操作日志注解
 * 用于标识需要记录日志的操作方法
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Loggable {

    /**
     * 操作类型，直接对应 SystemLog 的 actionType 字段
     */
    String actionType() default "unknown1";

    /**
     * 操作的详细描述，直接对应 SystemLog 的 actionDetail 字段
     */
    String actionDetail() default "unknown2";
}