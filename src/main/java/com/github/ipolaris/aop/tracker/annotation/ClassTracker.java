package com.github.ipolaris.aop.tracker.annotation;

import com.github.ipolaris.aop.tracker.recorder.DefaultTrackerRecorder;
import com.github.ipolaris.aop.tracker.recorder.ITrackerRecorder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 添加此注解，类的入口方法讲被加入aop
 * 如果子方法希望加入aop，请使用AopContext.currentProxy()获取代理类调用方法，或继承 {@link com.github.ipolaris.aop.tracker.utils.AbstractProxyTrackerUtil}使用stepTrackerProxy(this)获取代理
 * @author ipolaris
 * created at 2016年7月30日 下午3:08:35
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ClassTracker {
	/**
	 * 记录跟踪信息的recorder 默认为DefaultTrackerRecorder（记录输入输出）
	 * @return
	 */
	Class<? extends ITrackerRecorder> trackerRecorder() default DefaultTrackerRecorder.class;
}
