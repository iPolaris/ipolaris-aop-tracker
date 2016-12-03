功能介绍
	通过添加注解方式实现AOP方法的拦截，自定义ITrackerRecorder实现方法执行前后或抛出异常的逻辑。可用于日志记录等场景
	
使用方式：
	项目spring配置文件添加<import resource="classpath*:spring/application-ipolaris-aop-tracker.xml"></import>
	若先对类的方法实现拦截记录，可在类上添加ClassTracker注解，同时指定ITrackerRecorder的实现类，用于执行自定义的拦截行为，默认分配
	DefaultTrackerRecorder（简单的日志记录：入参，执行结果，异常记录，执行时间），当然我们使用Spring来帮我们实现了AOP，还要在类上添加Service或Component注解	
注：
	由于动态代理的实现原理，只会拦截到类被外部调用的方法，内部的方法调用（this）默认是拦截不到的，如果想拦截，可继承AbstractProxyTrackerUtil
	先使用stepTrackerProxy方法获取代理类，然后在执行内部方法，如：Test类里有A，B两个方法，A调用B，test.A()时，A会被拦截记录，B不会被拦截记录，
	此时若要B也被拦截，在A内调用B时，请不要使用this.B(),请使用stepTrackerProxy(this).B(),这样的话，B也会被拦截记录。如不继承stepTrackerProxy，
	也可以AopContext.currentProxy()拿到代理类，然后在调用B。
	
项目导入：
	本项目使用gradle构建，请在本地设置gradle环境（版本2.0以上）
	在ipolairs-aop-tracker下执行 gradle idea （idea项目） 或 gradle eclipse （eclipse项目） -----Linux或OSX环境下
	
jar上传：
	执行 gradle uploadArchives 会发布到本地.m2下，若发布到自己的maven仓库，请修改gradle文件，换成自己的maven仓库地址
	
	
----------------------------------------------------------------------------------------------------------
									若发现BUG欢迎指正，同时欢迎给出更好的修改意见
----------------------------------------------------------------------------------------------------------
