package com.pengshu.dubboi_example_java.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pengshu.dubboi_example_java.service.SayHello;

@Service(version = "1.0.0", group = "chinese")
public class ChineseSayHello implements SayHello {

	@Override
	public String sayHello(String name) {
		return "你好，" + name;
	}

}
