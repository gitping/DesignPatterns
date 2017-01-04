package com.yto.suixingouuser.activity.helper.model;

public class HttpRequestHeader {
	private String m;
	private HttpRequestBody p;
	public String getM() {
		return m;
	}
	public void setM(String m) {
		this.m = m;
	}
	public HttpRequestBody getP() {
		return p;
	}
	public void setP(HttpRequestBody p) {
		this.p = p;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	private String uuid;

}
