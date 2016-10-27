package com.tedu.cloudnote.dao;

import java.util.List;

import com.tedu.cloudnote.entity.Project;
import com.tedu.cloudnote.entity.Task;

public interface TaskDao {
	public int saveTask(List<Task> task);
	public List<Task>findUndo();
	public List<Task>findByPId(String p_id);
}
