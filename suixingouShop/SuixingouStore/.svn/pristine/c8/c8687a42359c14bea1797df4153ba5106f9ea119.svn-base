package com.yto.zhang.util.modle;



import org.apache.commons.lang.StringUtils;

public enum ExpressOrderStatusEnum {

	WAITING("0", "待接单"), RECEIVING("1", "已接单"), USER_CANCEL("2", "取消订单"), REJECTED(
			"3", "不接单"), HAVE_EXPRESS("4", "已取件"), TIMEOUT("5", "超时"), SUCCESS(
			"6", "已经完成"), UPDATE("7", "更新快递单");

	private String code;
	private String desc;

	private ExpressOrderStatusEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getNameByCode(String code) {
		for (ExpressOrderStatusEnum c : ExpressOrderStatusEnum.values()) {
			if (StringUtils.equals(c.getCode(), code)) {
				return c.getDesc();
			}
		}
		return null;
	}

	public static ExpressOrderStatusEnum getExpressOrderStatusEnumByCode(
			String code) {

		for (ExpressOrderStatusEnum expressOrderStatusEnum : ExpressOrderStatusEnum
				.values()) {

			if (StringUtils.equals(code, expressOrderStatusEnum.getCode())) {
				return expressOrderStatusEnum;
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
