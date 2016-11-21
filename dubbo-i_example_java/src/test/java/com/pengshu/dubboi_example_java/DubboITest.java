package com.pengshu.dubboi_example_java;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.pengshu.dubbo_i.conf.DubboI_Configuration;
import com.pengshu.dubbo_i.exception.RpcServiceException;
import com.pengshu.dubbo_i.service.RpcGenericService;

/**
 * 测试使用java的方式启动dubbo-i
 * 1.对服务实现类添加@Service注解
 * 2.指定dubbo-i配置文件
 * 3.手动指定需要服务注册的包
 * @author pengshu
 *
 */
public class DubboITest {
	
	private static final Logger LOGGER = Logger.getLogger(DubboITest.class);

	@BeforeClass
	public static void beforeClass() throws Exception {
		DubboI_Configuration dubboi = DubboI_Configuration.newInstance("dubboi.properties"); // 指定dubbo-i配置文件，默认启动restfull服务
		//DubboI_Configuration dubboi = DubboI_Configuration.newInstance("dubboi.properties", false); // 指定dubbo-i配置文件，不启动restfull服务
		dubboi.registerRpcServer("com.pengshu.dubboi_example_java.service.impl"); // 手动指定需要服务注册的包
		// dubboi.registerRpcServer(....); // 可以有多个
	}
	
	@Test
	public void testSayHelloImpl() {
		try {
			//RpcGenericService sayHelloService = RpcGenericService.Create("com.pengshu.dubboi_example_java.service.SayHello", "1.0.0"); // @Service中的版本会覆盖dubboi.properties中的版本
			RpcGenericService sayHelloService = RpcGenericService.Create("com.pengshu.dubboi_example_java.service.SayHello", "1.0.0", DubboI_Configuration.Loadbalance.random.toString(), 100, 10, 2, 0); // 获取service的同时，指定客户端的负载均衡等配置
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
			RpcGenericService sayHelloService = RpcGenericService.Create("com.pengshu.dubboi_example_java.service.UserService", "V1.00.01"); // dubboi.properties配置文件中的项目版本
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
