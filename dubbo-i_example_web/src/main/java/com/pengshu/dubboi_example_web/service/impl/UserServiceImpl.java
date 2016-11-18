package com.pengshu.dubboi_example_web.service.impl;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.gson.Gson;
import com.pengshu.dubboi_example_web.pojo.User;
import com.pengshu.dubboi_example_web.service.UserService;

@Service
@Component
public class UserServiceImpl implements UserService {
	
	private Gson gson = new Gson();
	
	@Override
	public String getUser(int id) {
		return gson.toJson(new User(id, "张三"));
	}

	@Override
	public String addUser(int id, String name) {
		return gson.toJson(new User(id, name));
	}

}
