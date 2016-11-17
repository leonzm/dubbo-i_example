package com.pengshu.dubboi_example_spring.service.impl;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.gson.Gson;
import com.pengshu.dubboi_example_spring.pojo.User;
import com.pengshu.dubboi_example_spring.service.UserService;

@Service
@Component
public class UserServiceImpl implements UserService {
	
	private Gson gson = new Gson();
	
	@Override
	public String getUser(Integer id) {
		return gson.toJson(new User(id, "张三"));
	}
	
}
