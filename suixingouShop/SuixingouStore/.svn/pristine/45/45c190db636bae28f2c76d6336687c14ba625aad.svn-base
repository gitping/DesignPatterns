package com.yto.zhang.util.modle;

import org.apache.commons.lang.StringUtils;


public enum OrderStatusEnum {

	WAITING("0", "待接单"), RECEIVING("1", "已接单"), DELIVERING("2", "配送中"), USER_CANCEL(
			"3", "用户取消"), REJECTED("4", "不接单"), TIMEOUT("5", "超时"), SUCCESS(
			"6", "交易结束");
	private String code;
	private String desc;

	private OrderStatusEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public static OrderStatusEnum getOrderStatusEnumByCode(String code) {
		for (OrderStatusEnum orderStatusEnum : OrderStatusEnum.values()) {

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
