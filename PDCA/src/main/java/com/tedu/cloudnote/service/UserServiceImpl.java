package com.tedu.cloudnote.service;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tedu.cloudnote.dao.UserDao;
import com.tedu.cloudnote.entity.User;
import com.tedu.cloudnote.util.NoteException;
import com.tedu.cloudnote.util.NoteResult;
import com.tedu.cloudnote.util.NoteUtil;

@Service("userService")//扫描
@Transactional
public class UserServiceImpl implements UserService{
	@Resource
	private UserDao userDao;//注入
	
	@Transactional(readOnly=true)
	public NoteResult checkLogin(
			String name, String password) {
		NoteResult result = new NoteResult();
		//判断用户名
		User user = userDao.findByName(name);
		if(user==null){
			result.setStatus(1);
			result.setMsg("用户名不存在");
			return result;
		}
		//判断密码
		//将用户输入的明文加密
		try{
			String md5_pwd = NoteUtil.md5(password);
			if(!user.getU_password()
					.equals(md5_pwd)){
				result.setStatus(2);
				result.setMsg("密码错误");
				return result;
			}
		}catch(Exception e){
			throw new NoteException(
				"密码加密异常", e);	
		}
		//登录成功
		result.setStatus(0);
		result.setMsg("登录成功");
		user.setU_password("");//把密码屏蔽不返回
		result.setData(user);//返回user信息
		return result;
	}

	@Transactional
	public NoteResult addUser(
		String name, String phone, String password) {
		NoteResult result = new NoteResult();
		try{
			//检测是否重名
			User has_user = userDao.findByName(name);
			User had_user=userDao.findByPhone(phone);
			if(has_user != null){
				result.setStatus(1);
				result.setMsg("用户名已被占用");
				return result;
			}else if(had_user!=null){
				result.setStatus(1);
				result.setMsg("手机号已被占用");
				return result;
			}
			//执行用户注册
			User user = new User();
			user.setU_name(name);//设置用户名
			user.setU_phone(phone);//设置手机号
			String md5_pwd = NoteUtil.md5(password);
			System.out.println("len:"+md5_pwd.length());
			user.setU_password(md5_pwd);//设置加密密码
			String userId = NoteUtil.createId();
			user.setU_uid(userId);//设置用户ID
			userDao.save(user);
			//创建返回结果
			result.setStatus(0);
			result.setMsg("注册成功");
			return result;
		}catch(Exception e){
			throw new NoteException("用户注册异常",e);
		}
		
	}

	public NoteResult searchUser(String name) {
		name="%"+name+"%";
		List<User>list=userDao.searchByName(name);
		NoteResult nr=new NoteResult();
		if(list==null){
			nr.setStatus(1);
			nr.setMsg("查询失败");
		}else{
			if(list.size()==0){
				nr.setStatus(1);
				nr.setMsg("查无此人");
			}else{
				nr.setStatus(0);
				nr.setMsg("查询成功");
				nr.setData(list);
			}
		}
		return nr;
	}

}
