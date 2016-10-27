package com.tedu.cloudnote.entity;

import java.sql.Timestamp;
import java.util.Date;

public class Project {
	private String p_id;
	private String p_name;
	private Timestamp p_createtime;
	private Integer p_status;
	private String u_uid;
	public String getP_id() {
		return p_id;
	}
	public void setP_id(String p_id) {
		this.p_id = p_id;
	}
	public String getP_name() {
		return p_name;
	}
	public void setP_name(String p_name) {
		this.p_name = p_name;
	}
	public Date getP_createtime() {
		return p_createtime;
	}
	public void setP_createtime(Timestamp p_createtime) {
		this.p_createtime = p_createtime;
	}
	public Integer getP_status() {
		return p_status;
	}
	public void setP_status(Integer p_status) {
		this.p_status = p_status;
	}
	public String getU_uid() {
		return u_uid;
	}
	public void setU_uid(String u_uid) {
		this.u_uid = u_uid;
	}
}
