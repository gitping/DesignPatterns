package com.yto.zhang.util.modle;

import java.util.Map;

/**
 * 响应数据json数据封装
 * 
 * @author longyong
 * 
 */
public class ResponseDTO {

	private Integer code;

	private Map<String, Object> result;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Map<String, Object> getResult() {
		return result;
	}

	public void setResult(Map<String, Object> result) {
		this.result = result;
	}

}
