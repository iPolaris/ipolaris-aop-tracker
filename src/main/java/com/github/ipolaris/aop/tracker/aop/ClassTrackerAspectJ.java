package com.github.ipolaris.aop.tracker.aop;

import com.github.ipolaris.aop.tracker.recorder.ITrackerRecorder;
import com.github.ipolaris.aop.tracker.annotation.ClassTracker;
import com.github.ipolaris.aop.tracker.utils.SpringAppContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * AOP拦截，切点为含有 {@link com.github.ipolaris.aop.tracker.annotation.ClassTracker}类的方法
 * @author ipolaris
 * created at 2016年7月30日 下午3:08:01
 */
@Component
@Aspect
public class ClassTrackerAspectJ {
    /**
     * 定义切点
     * @param classTracker
     */
    @Pointcut("@target(classTracker)")
    public void trackerPoint(ClassTracker classTracker){}

    /**
     * 方法执行前
     * @param joinPoint
     * @param classTracker
     */
    @Before("trackerPoint(classTracker)")
    public void beforeMethod(JoinPoint joinPoint, ClassTracker classTracker){
        ITrackerRecorder recorder = SpringAppContext.getBeanByBeanName(classTracker.trackerRecorder());
        recorder.recordBeforeMethod(joinPoint);
    }

    /**
     * 方法执行后
     * @param joinPoint
     * @param classTracker
     * @param retVal
     */
    @AfterReturning(pointcut = "trackerPoint(classTracker)", returning = "retVal")
    public void afterMethod(JoinPoint joinPoint, ClassTracker classTracker, Object retVal){
        ITrackerRecorder recorder = SpringAppContext.getBeanByBeanName(classTracker.trackerRecorder());
        recorder.recordAfterMethod(joinPoint, retVal);
    }

    /**
     * 方法抛出异常
     * @param joinPoint
     * @param exception
     * @param classTracker
     */
    @AfterThrowing(pointcut = "trackerPoint(classTracker)", throwing = "exception")
    public void afterMethodException(JoinPoint joinPoint, Throwable exception, ClassTracker classTracker){
        ITrackerRecorder recorder = SpringAppContext.getBeanByBeanName(classTracker.trackerRecorder());
        recorder.recordAfterMethodException(joinPoint, exception);
    }
}
