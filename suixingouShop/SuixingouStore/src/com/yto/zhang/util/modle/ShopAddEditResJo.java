package com.yto.zhang.util.modle;

import java.util.List;



/**
 * 添加修改商家
 * 
 * @author jingzhong
 * 
 */

public class ShopAddEditResJo extends Version {

	/**
	 * 
	 */
	private static long serialVersionUID = 1L;

	private Integer id;

	/**
	 * 店铺名称
	 */
	private String shopName;

	/**
	 * 纬度
	 */
	private String latitude;

	/**
	 * 经度
	 */
	private String longitude;

	/**
	 * 主营业务，全部使用id，分号分割
	 */
	private String saleRange;
	/**
	 * 省
	 */
	private String shopProvinceCode;
	/**
	 * 市
	 */
	private String shopCityCode;
	/**
	 * 区
	 */
	private String shopDistrictCode;
	/**
	 * 详细地址
	 */
	private String shopAddress;

	/**
	 * 服务时间
	 */
	private String serviceTimeDay;

	/**
	 * 服务开始时间
	 */
	private String serviceTimeBhour;
	/**
	 * 服务结束时间
	 */
	private String serviceTimeEhour;

	/**
	 * 是否有发票
	 */
	private String haveTips;
	
	/**
	 * 发票描述
	 */
	private String haveTipsDescribe;
	
	/**
	 * 店铺类型  1:社区百货店    2:餐饮店        3:便利店      4:奶茶店
	 */
	private Integer shopType;

	/**
	 * 是否是快递服务点
	 */
	private String isExpress;

	/**
	 * 网点编码
	 */
	private String ytoCode;

	/**
	 * 联系人
	 */
	private String contacter;

	/**
	 * 联系电话
	 */
	private String telephone;

	/**
	 * 配送时间（15分钟内配送）
	 */
	private String expressTime;

	/**
	 * 最低配送金额
	 */
	private Double minPrice;
	
	
	/**5.7 jz
	 * 服务距离
	 */
	private Double serviceDistance;
	
	/**5.7 jz
	 * 备注信息
	 */
	private String remark;
	
	/** 5-29 zl
	 * 是否开通免费短信
	 */
	private Integer freeMessage;
	/** 5-29 zl
	 * 获取免费短信手机号
	 */
	private String freePhoneNum;
	
	
	 private String shopStatus;
	 
	 
	 
	 
		//6.26最远配送
		private Double minPriceExtra;
		private String expressTimeExtra;
		private Double serviceDistanceExtra;
	 
	 
		/**
		 * 配送费
		 */
		private Double deliveryCost1;
		/**
		 * 配送费
		 */
		private Double deliveryCost2;
		/**
		 * 07-25
		 * zl--
		 * @return
		 */
		private String city;
		
		
	/**
	 * 08-11
	 * zl--返回店铺类型的bean
	 * @return
	 */
		private List<ShopTypeBean> shopTypeBeans;
	
	/**
	 * 是否有录入订单权限0：没有 1：有
	 */
	private String insertAuthority;
	
	/**
	 * 店铺是否关联分公司0：没有 1：有
	 */
	private String isRelated;
	



	public List<ShopTypeBean> getShopTypeBeans() {
		return shopTypeBeans;
	}

	public void setShopTypeBeans(List<ShopTypeBean> shopTypeBeans) {
		this.shopTypeBeans = shopTypeBeans;
	}

	public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

	public Double getMinPriceExtra() {
			return minPriceExtra;
		}

		public void setMinPriceExtra(Double minPriceExtra) {
			this.minPriceExtra = minPriceExtra;
		}

		public String getExpressTimeExtra() {
			return expressTimeExtra;
		}

		public void setExpressTimeExtra(String expressTimeExtra) {
			this.expressTimeExtra = expressTimeExtra;
		}

		public Double getServiceDistanceExtra() {
			return serviceDistanceExtra;
		}

		public void setServiceDistanceExtra(Double serviceDistanceExtra) {
			this.serviceDistanceExtra = serviceDistanceExtra;
		}

	public String getShopStatus() {
		return shopStatus;
	}

	public void setShopStatus(String shopStatus) {
		this.shopStatus = shopStatus;
	}

	public Integer getFreeMessage() {
		return freeMessage;
	}

	public void setFreeMessage(Integer freeMessage) {
		this.freeMessage = freeMessage;
	}

	public String getFreePhoneNum() {
		return freePhoneNum;
	}

	public void setFreePhoneNum(String freePhoneNum) {
		this.freePhoneNum = freePhoneNum;
	}

	public Double getServiceDistance() {
		return serviceDistance;
	}

	public void setServiceDistance(Double serviceDistance) {
		this.serviceDistance = serviceDistance;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getSaleRange() {
		return saleRange;
	}

	public void setSaleRange(String saleRange) {
		this.saleRange = saleRange;
	}

	public String getShopProvinceCode() {
		return shopProvinceCode;
	}

	public void setShopProvinceCode(String shopProvinceCode) {
		this.shopProvinceCode = shopProvinceCode;
	}

	public String getShopCityCode() {
		return shopCityCode;
	}

	public void setShopCityCode(String shopCityCode) {
		this.shopCityCode = shopCityCode;
	}

	public String getShopDistrictCode() {
		return shopDistrictCode;
	}

	public void setShopDistrictCode(String shopDistrictCode) {
		this.shopDistrictCode = shopDistrictCode;
	}

	public String getShopAddress() {
		return shopAddress;
	}

	public void setShopAddress(String shopAddress) {
		this.shopAddress = shopAddress;
	}

	public String getServiceTimeDay() {
		return serviceTimeDay;
	}

	public void setServiceTimeDay(String serviceTimeDay) {
		this.serviceTimeDay = serviceTimeDay;
	}

	public String getServiceTimeBhour() {
		return serviceTimeBhour;
	}

	public void setServiceTimeBhour(String serviceTimeBhour) {
		this.serviceTimeBhour = serviceTimeBhour;
	}

	public String getServiceTimeEhour() {
		return serviceTimeEhour;
	}

	public void setServiceTimeEhour(String serviceTimeEhour) {
		this.serviceTimeEhour = serviceTimeEhour;
	}

	public String getHaveTips() {
		return haveTips;
	}

	public void setHaveTips(String haveTips) {
		this.haveTips = haveTips;
	}

	public String getIsExpress() {
		return isExpress;
	}

	public void setIsExpress(String isExpress) {
		this.isExpress = isExpress;
	}

	public String getYtoCode() {
		return ytoCode;
	}

	public void setYtoCode(String ytoCode) {
		this.ytoCode = ytoCode;
	}

	public String getContacter() {
		return contacter;
	}

	public void setContacter(String contacter) {
		this.contacter = contacter;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getExpressTime() {
		return expressTime;
	}

	public void setExpressTime(String expressTime) {
		this.expressTime = expressTime;
	}

	public Double getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(Double minPrice) {
		this.minPrice = minPrice;
	}

	public Double getDeliveryCost1() {
		return deliveryCost1;
	}

	public void setDeliveryCost1(Double deliveryCost1) {
		this.deliveryCost1 = deliveryCost1;
	}

	public Double getDeliveryCost2() {
		return deliveryCost2;
	}

	public void setDeliveryCost2(Double deliveryCost2) {
		this.deliveryCost2 = deliveryCost2;
	}

	public String getHaveTipsDescribe() {
		return haveTipsDescribe;
	}

	public void setHaveTipsDescribe(String haveTipsDescribe) {
		this.haveTipsDescribe = haveTipsDescribe;
	}

	public Integer getShopType() {
		return shopType;
	}

	public void setShopType(Integer shopType) {
		this.shopType = shopType;
	}

	public String getInsertAuthority() {
		return insertAuthority;
	}

	public void setInsertAuthority(String insertAuthority) {
		this.insertAuthority = insertAuthority;
	}

	public String getIsRelated() {
		return isRelated;
	}

	public void setIsRelated(String isRelated) {
		this.isRelated = isRelated;
	}

}
