package com.pengshu.dubboi_example_web;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pengshu.dubbo_i.conf.DubboI_Configuration;
import com.pengshu.dubbo_i.util.JsonUtil;
import com.pengshu.dubboi_example_web.util.HttpUtil;

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
		//DubboI_Configuration dubboi = DubboI_Configuration.newInstance("dubboi.properties", DubboI_Configuration.Loadbalance.random); // 指定dubbo-i配置文件，设置服务端的负载均衡
		LOGGER.info("spring启动，加载服务完成"); // 随spring而启动
	}
	
	@Test
	public void testSayHelloImpl() { // get 方式
		String service = "com.pengshu.dubboi_example_web.service.SayHello";
		String method = "sayHello";
		int restfulPort = DubboI_Configuration.instance.getRestfulPort();
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("arg0", "王五");
		String url = "http://localhost:" + restfulPort + "?service=" + service + "&method=" + method + "&version=1.0.0" + "&parameters=" + JsonUtil.toJSON(parameters);
		
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		
		try {
			String result = HttpUtil.do_get(url, null, headers);
			System.out.println("testSayHelloImpl result: " + result);
			Assert.assertNotNull(result);
			Assert.assertEquals("{\"data\":\"Hello 王五\",\"success\":true,\"error\":null,\"errorType\":null}", result);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void testUserServiceImpl() { // post 方式
		String service = "com.pengshu.dubboi_example_web.service.UserService";
		String method = "addUser";
		int restfulPort = DubboI_Configuration.instance.getRestfulPort();
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("arg0", 4);
		parameters.put("arg1", "王五");
		String url = "http://localhost:" + restfulPort + "?service=" + service + "&method=" + method + "&version=V1.00.01";
		
		try {
			String result = HttpUtil.do_post_json(url, parameters, null);
			System.out.println("testUserServiceImpl result: " + result);
			Assert.assertNotNull(result);
			Assert.assertEquals("{\"data\":\"{\\\"id\\\":4,\\\"name\\\":\\\"王五\\\"}\",\"success\":true,\"error\":null,\"errorType\":null}", result);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@AfterClass
	public static void afterClass() {
		LOGGER.info("程序退出，服务下线");
	}
	
}
