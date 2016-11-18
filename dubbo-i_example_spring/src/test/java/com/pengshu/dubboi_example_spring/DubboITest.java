package com.pengshu.dubboi_example_spring;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pengshu.dubbo_i.conf.DubboI_Configuration;
import com.pengshu.dubbo_i.exception.RpcServiceException;
import com.pengshu.dubbo_i.service.RpcGenericService;

/**
 * 测试使用spring的方式启动dubbo-i
 * 1.对服务实现类添加@Service和@Component注解
 * 2.指定dubbo-i配置文件
 * 3.spring配置文件中添加对"com.pengshu.dubbo_i.server"目录和自己要暴露服务目录的扫描
 * 4.当项目启动spring后，dubbo-i也随之启动，会加载服务并注册
 * @author pengshu
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-test.xml")
public class DubboITest {
	
	private static final Logger LOGGER = Logger.getLogger(DubboITest.class);

	@BeforeClass
	public static void beforeClass() throws Exception {
		DubboI_Configuration.newInstance("dubboi.properties"); // 指定dubbo-i配置文件，默认启动restfull服务
		//DubboI_Configuration.newInstance("dubboi.properties", false); // 指定dubbo-i配置文件，不启动restfull服务
		LOGGER.info("spring启动，加载服务完成"); // 随spring而启动
	}
	
	@Test
	public void testSayHelloImpl() {
		try {
			RpcGenericService sayHelloService = RpcGenericService.Create("com.pengshu.dubboi_example_spring.service.SayHello", "1.0.0"); // @Service中的版本会覆盖dubboi.properties中的版本
			Object result = sayHelloService.invoke("sayHello", "李四");
			System.out.println("testSayHelloImpl result: " + result);
			Assert.assertNotNull(result);
			Assert.assertEquals("Hello 李四", result.toString());
		} catch (RpcServiceException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void testUserServiceImpl() {
		try {
			RpcGenericService sayHelloService = RpcGenericService.Create("com.pengshu.dubboi_example_spring.service.UserService", "V1.00.01"); // dubboi.properties配置文件中的项目版本
			Object result = sayHelloService.invoke("getUser", 1);
			System.out.println("testUserServiceImpl result: " + result);
			Assert.assertNotNull(result);
			Assert.assertEquals("{\"id\":1,\"name\":\"张三\"}", result.toString());
		} catch (RpcServiceException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@AfterClass
	public static void afterClass() {
		LOGGER.info("程序退出，服务下线");
	}
	
}
