package com.yto.suixingouuser.model;

/**
 * 更新
 * 
 * @author 12345678
 */
public class VersionDatasBean {
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	private int ver;
	private String url;
	private String memo;
	private String vername;

	public int getVer() {
		return ver;
	}

	public void setVer(int ver) {
		this.ver = ver;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getVername() {
		return vername;
	}

	public void setVername(String vername) {
		this.vername = vername;
	}
}
