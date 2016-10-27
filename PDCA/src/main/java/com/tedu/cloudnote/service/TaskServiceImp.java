package com.tedu.cloudnote.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.annotation.Resources;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tedu.cloudnote.dao.ProjectDao;
import com.tedu.cloudnote.dao.TaskDao;
import com.tedu.cloudnote.dao.UserDao;
import com.tedu.cloudnote.entity.Project;
import com.tedu.cloudnote.entity.Task;
import com.tedu.cloudnote.entity.User;
import com.tedu.cloudnote.util.NoteResult;
import com.tedu.cloudnote.util.NoteUtil;

@Service("taskService")//扫描
@Transactional
public class TaskServiceImp implements TaskService{
	@Resource
	private TaskDao taskDao;
	@Resource
	private UserDao useDao;
	@Resource
	private ProjectService pj;
	//发送任务表单给指定的人
	public NoteResult send(String tableList, String accpect,String title) {
		NoteResult nr=new NoteResult();
		//通过名字找到对于的u_uid
		User user=useDao.findByName(accpect);
		if(user==null){
			nr.setMsg("接收人不存在");
			nr.setStatus(1);
			return nr;
		}
		//接受任务放的u_uid
		String u_uid=user.getU_uid();
		
		String []list=tableList.split(";");
		System.out.println("lengtj"+list.length);
		String p_id=NoteUtil.createId();
		List<Task>listTask=new ArrayList<Task>();
		for(int i=0;i<list.length;i++){
			Task task=new Task();
			String []attrs=list[i].split(",");
			//获取数据值
			//设置任务的内容
			task.setT_body(attrs[0]);
			//设置任务的计划
			task.setT_plan(attrs[1]);
			//设置任务的标准
			task.setT_standard(attrs[2]);
			//设置任务的检查时间
			task.setT_time(attrs[3]);
			//设置任务的就检查人
			task.setT_checkP(attrs[4]);
			task.setU_uid(u_uid);
			task.setT_id(NoteUtil.createId());
			//设置状态码
			task.setT_status(0);
			//设置任务的项目id
			task.setP_id(p_id);
			listTask.add(task);
		}
		NoteResult r=pj.saveProject(p_id, title, u_uid);
		if(r.getStatus()!=0){
			nr.setStatus(1);
			nr.setMsg("发送失败");
			return nr;
		}
		int i=taskDao.saveTask(listTask);
		if(i>0){
			nr.setStatus(0);
			nr.setMsg("发送成功");
		}else{
			nr.setStatus(1);
			nr.setMsg("发送失败");
		}
		return nr;
	}
	public NoteResult findUndo() {
		NoteResult nr=new NoteResult();
		List<Task>list=taskDao.findUndo();
		if(list==null){
			nr.setStatus(1);
			nr.setMsg("查询未完成的工作失败");
		}else{
			if(list.size()==0){
				nr.setStatus(1);
				nr.setMsg("查无结果");
			}else{
				nr.setStatus(0);
				nr.setMsg("查询未完成的工作成功");
				nr.setData(list);
			}
		}
		return nr;
	}
	public NoteResult findByPId(String p_id) {
		List<Task>list=taskDao.findByPId(p_id);
		NoteResult nr=new NoteResult();
		if(list==null){
			nr.setMsg("查询失败");
			nr.setStatus(1);
		}else if(list.size()==0){
			nr.setMsg("无此记录");
			nr.setStatus(1);	
		}else{
			nr.setMsg("查询任务项目表成功");
			nr.setStatus(0);
			nr.setData(list);
		}
		return nr;
	}

}
