package com.tedu.cloudnote.controller.user;

import java.awt.image.BufferedImage;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tedu.cloudnote.service.UserService;
import com.tedu.cloudnote.util.NoteResult;
import com.tedu.cloudnote.util.NoteUtil;

@Controller//扫描
public class UserController {
	@Resource//注入
	private UserService userService;
	//添加
	@RequestMapping("/user/add.do")
	@ResponseBody
	public NoteResult execute(
		String name,String password,String phone){
		NoteResult result = 
			userService.addUser(
			name, phone, password);
		return result;
	}
	//登陆
	@RequestMapping("/user/login.do")
	@ResponseBody
	public NoteResult login(String name,String password){
			return userService.checkLogin(name, password);
	}
	//通过用户名的模糊查询
	@RequestMapping("/user/likeFindName.do")
	@ResponseBody
	public NoteResult execute(String name){
		return	userService.searchUser(name);
	}
	
	
}
