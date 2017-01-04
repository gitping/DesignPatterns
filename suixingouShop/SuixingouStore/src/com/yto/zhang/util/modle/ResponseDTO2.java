package com.yto.zhang.util.modle;

import java.util.List;

/**
 * 响应数据json数据封装
 * 
 * @author longyong
 * 
 */
public class ResponseDTO2<T1, T2> {

	private Integer code;
	private Integer total;
	private List<T1> list;
	private T2 t2;
	/** 提示语 */
	private String prompt;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List<T1> getList() {
		return list;
	}

	public void setList(List<T1> list) {
		this.list = list;
	}

	public T2 getT2() {
		return t2;
	}

	public void setT2(T2 t2) {
		this.t2 = t2;
	}

	public String getPrompt() {
		return prompt;
	}

	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

}
