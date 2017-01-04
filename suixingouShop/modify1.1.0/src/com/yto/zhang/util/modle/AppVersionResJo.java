package com.yto.zhang.util.modle;




/**
 * app 版本控制
 * 
 * @author jingzhong
 * 
 */
public class AppVersionResJo extends Version {

	/**
	 * id
	 */
	private Integer id;
	
	/**
	 * 版本编码
	 */
	private Integer versionCode;
	
	/**
	 * 版本名称
	 */
	private String versionName;
		
	/**
	 * 版本说明
	 */
	private String versionDesc;
		
	/**
	 * 下载地址
	 */
	private String downloadUrl;
		
	/**
	 * 创建者id
	 */
	private Integer adminId;
	
	/**
	 * 是否强制更新1 强制更新 0否
	 */
	private String forceUpdate;
	
	

	

	public String getForceUpdate() {
		return forceUpdate;
	}

	public void setForceUpdate(String forceUpdate) {
		this.forceUpdate = forceUpdate;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public String getVersionDesc() {
		return versionDesc;
	}

	public void setVersionDesc(String versionDesc) {
		this.versionDesc = versionDesc;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(Integer versionCode) {
		this.versionCode = versionCode;
	}

	public Integer getAdminId() {
		return adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}

	
	
	
		

	
	
}
