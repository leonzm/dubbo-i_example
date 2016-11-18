package com.pengshu.dubboi_example_web.service.impl;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.pengshu.dubboi_example_web.service.SayHello;

@Service(version = "1.0.0")
@Component
public class SayHelloImpl implements SayHello {

	@Override
	public String sayHello(String name) {
		return "Hello " + name;
	}

}
