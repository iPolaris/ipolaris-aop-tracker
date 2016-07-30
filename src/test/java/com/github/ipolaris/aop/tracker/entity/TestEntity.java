package com.github.ipolaris.aop.tracker.entity;

import org.springframework.stereotype.Component;

import com.github.ipolaris.aop.tracker.annotation.ClassTracker;
import com.github.ipolaris.aop.tracker.utils.AbstractProxyTrackerUtil;

/**
 * 
 * @author ipolaris
 * created at 2016年7月30日 下午3:10:03
 */
@ClassTracker
@Component
public class TestEntity extends AbstractProxyTrackerUtil {
    public void test(){
        System.out.println("test method");
        stepTrackerProxy(this).test2("t1","t2",3);
        throw new NullPointerException("test");
    }
    public String test2(String t1, String t2, int t3){
        return "Hello";
    }
}
