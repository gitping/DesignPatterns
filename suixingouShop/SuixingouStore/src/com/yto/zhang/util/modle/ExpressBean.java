package com.yto.zhang.util.modle;

import java.io.Serializable;

import net.tsz.afinal.annotation.sqlite.Table;


@Table(name="expressname")
public class ExpressBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int exId;
	private String exName;
	private String exCode;
	private String exPic;
	private int click;
	private String sortLetters;
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getExId() {
		return exId;
	}
	public void setExId(int exId) {
		this.exId = exId;
	}
	public String getExName() {
		return exName;
	}
	public void setExName(String exName) {
		this.exName = exName;
	}
	public String getExCode() {
		return exCode;
	}
	public void setExCode(String exCode) {
		this.exCode = exCode;
	}
	public String getExPic() {
		return exPic;
	}
	public void setExPic(String exPic) {
		this.exPic = exPic;
	}
	public int getClick() {
		return click;
	}
	public void setClick(int click) {
		this.click = click;
	}
	public String getSortLetters() {
		return sortLetters;
	}
	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}
	
	
	

}
