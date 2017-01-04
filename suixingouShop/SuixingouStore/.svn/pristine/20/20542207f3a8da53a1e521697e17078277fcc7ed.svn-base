package com.yto.zhang.util.modle;

import org.apache.commons.lang.StringUtils;





public enum CollectOrderStatusEnum {

	WAITING("0", "待取件"), RUNNING("1", "取件中"), SUCCESS("2", "已取件"), EXCEPTION("3", "取件异常");
	
	

	private String code;
	private String desc;

	private CollectOrderStatusEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public static CollectOrderStatusEnum getOrderStatusEnumByCode(String code) {

		for (CollectOrderStatusEnum orderStatusEnum : CollectOrderStatusEnum.values()) {

			if (StringUtils.equals(code, orderStatusEnum.getCode())) {
				return orderStatusEnum;
			}
		}

		return null;

	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
