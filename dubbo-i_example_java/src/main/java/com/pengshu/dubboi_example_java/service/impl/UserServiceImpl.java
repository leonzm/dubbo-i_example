package com.pengshu.dubboi_example_java.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.gson.Gson;
import com.pengshu.dubboi_example_java.pojo.User;
import com.pengshu.dubboi_example_java.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	private Gson gson = new Gson();
	
	@Override
	public String getUser(Integer id) {
		return gson.toJson(new User(id, "张三"));
	}
	
}
