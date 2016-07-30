package com.github.ipolaris.aop.tracker;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.ipolaris.aop.tracker.entity.TestEntity;

/**
 * 
 * @author ipolaris
 * created at 2016年7月30日 下午3:09:53
 */
public class TrackerTest extends BaseTest{
    @Autowired
    private TestEntity entity;

    @Test(expected = NullPointerException.class)
    public void test(){
        entity.test();
    }

}
