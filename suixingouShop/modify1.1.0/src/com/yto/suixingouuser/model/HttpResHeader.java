package com.yto.suixingouuser.model;

/**
 * 服务器返回JsonHeader 
 * 发表视频评论的返回Json
 * 用户删除一条点播的视频
 * 用户删除全部点播的视频
 * 用户收藏视频
 * 用户是否收藏视频
 * 用户删除一条收藏的视频
 * 用户清空收藏的视频
 * APP意见反馈保存
 * 用户注册
 * 手机验证码短信
 * 用户信息编辑
 * 依据手机号和发送的验证码短信更新密码
 * */

public class HttpResHeader {
	
	private int ret;
	private String msg;

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
}
