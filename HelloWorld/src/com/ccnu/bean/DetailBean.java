package com.ccnu.bean;

import java.io.Serializable;

public class DetailBean implements Serializable{
	private int did;
	private String ddate;
	private Double dmoney;
	private String dtype;
	private Double d_pre_balance;
	private Double dbalance;
	private String aid;
	private String dcount;
	
	public DetailBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DetailBean(int did,String ddate,Double dmoney,String dtype,
			Double d_pre_balance,Double dbalance,String aid, String dcount) {
		super();
		
		this.did = did;
		this.ddate = ddate;
		this.dmoney = dmoney;
		this.dtype = dtype;
		this.d_pre_balance = d_pre_balance;
		this.dbalance = dbalance;
		this.aid = aid;
		this.dcount = dcount;
	}
	public int getDid() {
		return did;
	}
	public void setDid(int did) {
		this.did = did;
	}
	public String getDdate() {
		return ddate;
	}
	public void setDdate(String ddate) {
		this.ddate = ddate;
	}
	public Double getDmoney() {
		return dmoney;
	}
	public void setDmoney(Double dmoney) {
		this.dmoney = dmoney;
	}
	public String getDtype() {
		return dtype;
	}
	public void setDtype(String dtype) {
		this.dtype = dtype;
	}
	public Double getD_pre_balance() {
		return d_pre_balance;
	}
	public void setD_pre_balance(Double dPreBalance) {
		d_pre_balance = dPreBalance;
	}
	public Double getDbalance() {
		return dbalance;
	}
	public void setDbalance(Double dbalance) {
		this.dbalance = dbalance;
	}
	public String getAid() {
		return aid;
	}
	public void setAid(String aid) {
		this.aid = aid;
	}
	public String getDcount() {
		return dcount;
	}
	public void setDcount(String dcount) {
		this.dcount = dcount;
	}
	
	
}
