package com.github.ipolaris.aop.tracker.utils;

import org.springframework.aop.framework.AopContext;

/**
 * 抽象类，增加获取代理的方法
 * @author ipolaris
 * created at 2016年7月30日 下午3:09:32
 */
public abstract class AbstractProxyTrackerUtil {
	@SuppressWarnings("unchecked")
	public <T> T stepTrackerProxy(T target){
		return (T)AopContext.currentProxy();
	}
}
