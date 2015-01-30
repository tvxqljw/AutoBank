package com.ccnu.bean;

import java.io.Serializable;

public class CustomerBean implements Serializable{

	private String cid;
	private String cname;
	private String csex;
	private String ctel;
	private String caddr;
	private String ccareer;
	private String cstate;
	private String cdate;
	
	public CustomerBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CustomerBean(String cid, String cname, String csex, String ctel,
			String caddr, String ccareer, String cstate, String cdate) {
		super();
		this.cid = cid;
		this.cname = cname;
		this.csex = csex;
		this.ctel = ctel;
		this.caddr = caddr;
		this.ccareer = ccareer;
		this.cstate = cstate;
		this.cdate = cdate;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getCsex() {
		return csex;
	}
	public void setCsex(String csex) {
		this.csex = csex;
	}
	public String getCtel() {
		return ctel;
	}
	public void setCtel(String ctel) {
		this.ctel = ctel;
	}
	public String getCaddr() {
		return caddr;
	}
	public void setCaddr(String caddr) {
		this.caddr = caddr;
	}
	public String getCcareer() {
		return ccareer;
	}
	public void setCcareer(String ccareer) {
		this.ccareer = ccareer;
	}
	public String getCstate() {
		return cstate;
	}
	public void setCstate(String cstate) {
		this.cstate = cstate;
	}
	public String getCdate() {
		return cdate;
	}
	public void setCdate(String cdate) {
		this.cdate = cdate;
	}

	

}
