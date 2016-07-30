package com.github.ipolaris.aop.tracker.recorder;

import org.aspectj.lang.JoinPoint;

/**
 * 定义接口，AOP拦截后的操作
 * @author ipolaris
 * created at 2016年7月30日 下午3:09:14
 */
public interface ITrackerRecorder {
    /**
     * 方法执行前
     * @param joinPoint
     */
    public void recordBeforeMethod(JoinPoint joinPoint);

    /**
     * 方法执行后
     * @param joinPoint
     */
    public void recordAfterMethod(JoinPoint joinPoint, Object retVal);

    /**
     * 方法抛出异常
     * @param joinPoint
     * @param throwable
     */
    public void recordAfterMethodException(JoinPoint joinPoint, Throwable throwable);
}
