package com.frame.sxgou.model;

import java.util.HashMap;

/**
 * 请求数据json解析格式
 * @author longyong
 *
 */
public class RequestDTO {

	private String m;
	
	private String uuid;


	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	private HashMap<String, Object> p;


	public String getM() {
		return m;
	}

	public void setM(String m) {
		this.m = m;
	}


	public HashMap<String, Object> getP() {
		return p;
	}

	public void setP(HashMap<String, Object> p) {
		this.p = p;
	}


}
