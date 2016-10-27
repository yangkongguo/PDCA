package com.tedu.cloudnote.controller.task;



import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tedu.cloudnote.service.TaskService;
import com.tedu.cloudnote.util.NoteResult;



@Controller//扫描
public class TaskController {
	@Resource//注入
	private TaskService taskService;
	
	@RequestMapping("/user/do_table.do")
	@ResponseBody
	public NoteResult findUndo(){
		return taskService.findUndo();
	}
	@RequestMapping("/user/send_table.do")
	@ResponseBody
	public NoteResult send(String tableList,String accpect,String title){
		return taskService.send(tableList, accpect,title);
	}
	
}
