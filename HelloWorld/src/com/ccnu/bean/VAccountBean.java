package com.ccnu.bean;

import java.io.Serializable;

public class VAccountBean implements Serializable{
	private String aid;
	private String cid;
	private String astate;
	private String apassword;
	private Double abalance;
	private String cname;
	private String adate;
	private String atype;
	public VAccountBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getAdate() {
		return adate;
	}
	public void setAdate(String adate) {
		this.adate = adate;
	}
	public String getAid() {
		return aid;
	}
	public void setAid(String aid) {
		this.aid = aid;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getAstate() {
		return astate;
	}
	public void setAstate(String astate) {
		this.astate = astate;
	}
	public String getApassword() {
		return apassword;
	}
	public void setApassword(String apassword) {
		this.apassword = apassword;
	}
	public Double getAbalance() {
		return abalance;
	}
	public void setAbalance(Double abalance) {
		this.abalance = abalance;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getType() {
		return atype;
	}
	public void setType(String atype) {
		this.atype = atype;
	}
	
	
	
}
