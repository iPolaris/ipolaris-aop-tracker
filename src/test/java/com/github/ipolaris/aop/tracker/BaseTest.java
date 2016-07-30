package com.github.ipolaris.aop.tracker;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = {"classpath*:spring/application*.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class BaseTest {
	
}
