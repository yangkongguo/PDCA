package com.tedu.cloudnote.controller.project;



import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tedu.cloudnote.service.ProjectService;
import com.tedu.cloudnote.service.TaskService;
import com.tedu.cloudnote.util.NoteResult;



@Controller//扫描
public class ProjectController {
	@Resource//注入
	private ProjectService projectService;
	
	@RequestMapping("/table/findUndoProjectByU_uid.do")
	@ResponseBody
	public NoteResult findUndoProject(String u_uid){
		return projectService.findUndoProject(u_uid);
	}
	
	@RequestMapping("/table/findFinshProjectByUid.do")
	@ResponseBody
	public NoteResult findProjectByUid(String u_uid){
		return projectService.findFinshProject(u_uid);
	}
	@RequestMapping("/table/finshProject.do")
	@ResponseBody
	public NoteResult updateProjectStatus(String u_uid,String p_name){
		return projectService.updateProjectStatus(u_uid, p_name);
	}
	
}
