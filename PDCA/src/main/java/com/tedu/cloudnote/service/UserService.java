package com.tedu.cloudnote.service;

import com.tedu.cloudnote.util.NoteResult;

public interface UserService {
	public NoteResult addUser(
		String name,String nick,String password);
	public NoteResult checkLogin(
		String name,String password);
	public NoteResult searchUser(String name);
}
