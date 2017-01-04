package com.yto.zhang.util.modle;




/**
 * 发系统消息
 * 
 * @author longyong
 * 
 */
public class MsgNewReqJo extends UUID {

	private String msgType;// 0:催用户取件
	private Integer objId;// 对象id

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public Integer getObjId() {
		return objId;
	}

	public void setObjId(Integer objId) {
		this.objId = objId;
	}

}
