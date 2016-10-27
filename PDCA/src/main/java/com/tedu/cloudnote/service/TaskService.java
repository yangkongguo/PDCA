package com.tedu.cloudnote.service;

import java.util.List;

import com.tedu.cloudnote.util.NoteResult;

public interface TaskService {
	public NoteResult send(String tableList,String accpect,String title);
	public NoteResult findUndo();
	public NoteResult findByPId(String p_id);
}
