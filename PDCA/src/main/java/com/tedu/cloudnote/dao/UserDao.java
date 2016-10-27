package com.tedu.cloudnote.dao;

import java.util.List;

import com.tedu.cloudnote.entity.User;

public interface UserDao {
	public void save(User user);
	public User findByName(String name);
	//查找的指定员工的下属信息根据名字 模糊查询
	public List<User> searchByName(String name);
	//查询手机号
	public User findByPhone(String phone);
}
