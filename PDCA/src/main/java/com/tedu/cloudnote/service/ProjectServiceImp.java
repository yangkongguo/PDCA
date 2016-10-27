package com.tedu.cloudnote.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tedu.cloudnote.dao.ProjectDao;
import com.tedu.cloudnote.dao.TaskDao;
import com.tedu.cloudnote.entity.Project;
import com.tedu.cloudnote.entity.Task;
import com.tedu.cloudnote.util.NoteResult;
@Service("projectService")//扫描
@Transactional
public class ProjectServiceImp implements ProjectService{
	@Resource
	private ProjectDao projectDao;
	@Resource
	private TaskDao taskDao;
	@Resource
	private TaskService taskService;
	//上传项目
	public NoteResult saveProject(String p_id, String p_name, String u_uid) {
		NoteResult nr=new NoteResult();
		Project project=new Project();
		project.setP_createtime(new Timestamp(System.currentTimeMillis()));
		project.setP_id(p_id);
		project.setU_uid(u_uid);
		project.setP_status(0);
		project.setP_name(p_name);
		int i=projectDao.saveProject(project);
		
		if(i>0){
			nr.setStatus(0);
		}else{
			nr.setStatus(1);
		}
		return nr;
	}
	//查找未完成的项目
	public NoteResult findUndoProject(String u_uid) {
		Project pj=projectDao.findUndoProject(u_uid);
		NoteResult nr=new NoteResult();
		System.out.println(pj==null?"null":pj.getP_id());
		if(pj==null){
			nr.setStatus(1);
			nr.setMsg("改用户暂时还没有工作安排");
			return nr;
		}
		String p_id=pj.getP_id();
		String p_name=pj.getP_name();
		nr=taskService.findByPId(p_id);
		nr.setMsg(p_name);
		return nr;
	}
	//查找已完成的项目
	public NoteResult findFinshProject(String u_uid){
		//存放所有的完成任务大p_id
		List<Map<String, String>>map=projectDao.findFinshProject(u_uid);
		NoteResult nr=new NoteResult();
		if(map==null){
			nr.setStatus(1);
			nr.setMsg("查询异常");
			return nr;
		}
		if(map.size()==0){
			nr.setMsg("没有任务完成的任务");
			nr.setStatus(2);
			nr.setData(map);
			return nr;
		}
			//通过pid查询已完成的工作
			List<Map<String, Object>>ll_task=new ArrayList<Map<String,Object>>();
			for(int i=0;i<map.size();i++){
				//将任务数据存入
				List<Task>task_list=taskDao.findByPId(map.get(i).get("p_id"));
				//将项目名存入
				String p_name=map.get(i).get("p_name");
				Map<String, Object> t_map=new HashMap<String, Object>();
				t_map.put("p_name", p_name);
				t_map.put("task_list", task_list);
				ll_task.add(t_map);
			}
			if(ll_task.size()==0){
				nr.setStatus(1);
				nr.setMsg("查询异常");
				return nr;
			}
			nr.setMsg("查询成功");
			nr.setData(ll_task);
			nr.setStatus(0);
			return nr;
	}
	public NoteResult updateProjectStatus(String u_uid, String p_name) {
		NoteResult nr=new NoteResult();
		Map<String, String>map =new HashMap<String, String>();
		map.put("u_uid", u_uid);
		map.put("p_name", p_name);
		int i=projectDao.updateProjectStatus(map);
		if(i>0){
			nr.setMsg("项目完成状态成功");
			nr.setStatus(0);
		}else{
			nr.setMsg("项目完成状态失败");
			nr.setStatus(1);
		}
		return nr;
	}

}
