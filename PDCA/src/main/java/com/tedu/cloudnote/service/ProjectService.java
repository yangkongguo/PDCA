package com.tedu.cloudnote.service;

import com.tedu.cloudnote.util.NoteResult;

public interface ProjectService {
	public NoteResult saveProject(String p_id,String p_name,String u_uid);
	public NoteResult findUndoProject(String u_uid);
	public NoteResult findFinshProject(String u_uid);
	public NoteResult updateProjectStatus(String u_uid,String p_name);
}
