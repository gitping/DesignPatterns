package com.yto.suixingouuser.model;

/**版本
 * @author 12345678
 */
public class VersionBean {
	private int ret;
	private String msg;
	private int forceupdate;
	private VersionDatasBean datas;
	private boolean needUpdate;
	
	public boolean isNeedUpdate() {
		return needUpdate;
	}
	public void setNeedUpdate(boolean needUpdate) {
		this.needUpdate = needUpdate;
	}
	public int getRet() {
		return ret;
	}
	public void setRet(int ret) {
		this.ret = ret;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getForceupdate() {
		return forceupdate;
	}
	public void setForceupdate(int forceupdate) {
		this.forceupdate = forceupdate;
	}
	public VersionDatasBean getDatas() {
		return datas;
	}
	public void setDatas(VersionDatasBean datas) {
		this.datas = datas;
	}

}
