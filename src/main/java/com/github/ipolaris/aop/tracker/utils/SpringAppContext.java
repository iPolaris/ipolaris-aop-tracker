package com.github.ipolaris.aop.tracker.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 此类作为一个工具类，实现ApplicationContextAware接口，spring初始化时候自动设置ApplicationContext
 *
 * 此类将作为一种显式调用spring工厂获取实例的工具
 */
public class SpringAppContext implements ApplicationContextAware {
	public static ConfigurableApplicationContext springAppContext;
	
	@Override
	public void setApplicationContext(ApplicationContext springAppContext) throws BeansException {
		SpringAppContext.springAppContext = (ConfigurableApplicationContext) springAppContext;
	}
	/**
	 * 提供一个获取spring ApplicationContext 对象的方法
	 * @return
	 */
	public static ApplicationContext getSpringAppContext(){
		return SpringAppContext.springAppContext;
	}
	/**
	 * 提供一个动态获取Bean的方法！
	 * @param beanId
	 * @return
	 * @throws BeansException
	 */
	@SuppressWarnings("unchecked")
	public static <T>T getBean(String beanId) throws BeansException{
		return (T)SpringAppContext.springAppContext.getBean(beanId);
	}

	public static <T>T getBeanByBeanName(Class<T> arg) throws BeansException{
		String beanName = arg.getSimpleName();
		String lBeanName = beanName.replaceFirst(beanName.substring(0, 1), beanName.substring(0, 1).toLowerCase());
		if (!springAppContext.containsBeanDefinition(beanName) && !springAppContext.containsBean(lBeanName)){
			register(arg, lBeanName);
		}
		return getBean(lBeanName);
	}


	public static <T>T getBean(Class<T> arg){
		return (T)springAppContext.getBean(arg);
	}

	public static <T> void register(Class<T> arg, String autoName){
		BeanDefinition bean = new GenericBeanDefinition();
		bean.setBeanClassName(arg.getName());
		DefaultListableBeanFactory fty = (DefaultListableBeanFactory) springAppContext.getAutowireCapableBeanFactory();
		//注册Bean
		fty.registerBeanDefinition(autoName, bean);
	}
}