package com.yto.zhang.util.modle;

import java.util.HashMap;

/**
 * 请求数据json解析格式
 * @author longyong
 *
 */
public class RequestDTO {

	private String m;

	private String uuid;

	private HashMap<String, Object> p;


	public String getM() {
		return m;
	}

	public void setM(String m) {
		this.m = m;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public HashMap<String, Object> getP() {
		return p;
	}

	public void setP(HashMap<String, Object> p) {
		this.p = p;
	}


}
