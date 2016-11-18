package com.pengshu.dubboi_example_web.launcher;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pengshu.dubbo_i.conf.DubboI_Configuration;

public class Launcher {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		// 指定dubbo-i配置文件
		DubboI_Configuration.newInstance("dubboi.properties");
		// 启动dubbo-i
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"spring-test.xml"});
		context.start();
		
	    System.in.read(); // 按任意键退出
	}
	
}
