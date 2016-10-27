package com.tedu.cloudnote.dao;


import java.util.List;
import java.util.Map;

import com.tedu.cloudnote.entity.Project;

public interface ProjectDao {
	public int saveProject(Project project);
	public Project findUndoProject(String u_uid);
	public List<Map<String,String >> findFinshProject(String u_uid);
	public int updateProjectStatus(Map<String, String>map);
}
