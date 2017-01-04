package com.yto.suixingouuser.model;

import java.io.Serializable;

import com.frame.lib.utils.SPUtils;

/**
 * 店铺详情存储内存和sp
 * @author ShenHua
 * 2015年5月22日下午1:39:31
 */
public class ShopDetail implements Serializable{
	
	private String imei;					//手机设备id
	private String uuid;					//用户保存登录的id
	private String mobil;					//用户登录的手机号码
	private String authority;				//用户是否有权限入单

	private long shopId;					//店铺id
	private String shopName;				//店铺名称
	private String shopAddress;				//店铺地址
	private String contactName;				//店铺联系人姓名
	private long shopType;					//店铺类型
	private String isExpress;				//是否可代寄快递
	private String ytoCode;					//圆通网点编码
	private String isRelated;				//店铺是否关联分公司0：没有 1：有
	private String pickupSMS;				//取件通知短信模板
	private String reminderSMS;				//包裹催收短信模板
	private String province;				//省
	private String provinceCode;			//省的code
	private String city;					//市
	private String cityCode;				//市的code
		
	public String getImei() {
		if(imei == null||"".equals(imei)){
			imei = SPUtils.getStringValue("imei");
		}
		return imei;
	}
	public void setImei(String imei) {
		SPUtils.saveStringValue("imei", imei);
		this.imei = imei;
	}
	public String getUuid() {
		if(uuid == null||"".equals(uuid)){
			uuid = SPUtils.getStringValue("uuid");
		}
		return uuid;
	}
	public void setUuid(String uuid) {
		SPUtils.saveStringValue("uuid", uuid);
		this.uuid = uuid;
	}
	public String getMobil() {
		if(mobil == null||"".equals(mobil)){
			mobil = SPUtils.getStringValue("mobil");
		}
		return mobil;
	}
	public void setMobil(String mobil) {
		SPUtils.saveStringValue("mobil", mobil);
		this.mobil = mobil;
	}
	public String getAuthority() {
		if(authority == null||"".equals(authority)){
			authority = SPUtils.getStringValue("authority");
		}
		return authority;
	}
	public void setAuthority(String authority) {
		SPUtils.saveStringValue("authority", authority);
		this.authority = authority;
	}
	public long getShopId() {
		if(shopId == 0){
			shopId = SPUtils.getLongValue("shopId");
		}
		return shopId;
	}
	public void setShopId(long shopId) {
		SPUtils.saveLongValue("shopId", shopId);
		this.shopId = shopId;
	}
	public String getShopName() {
		if(shopName == null||"".equals(shopName)){
			shopName = SPUtils.getStringValue("shopName");
		}
		return shopName;
	}
	public void setShopName(String shopName) {
		SPUtils.saveStringValue("shopName", shopName);
		this.shopName = shopName;
	}
	public String getShopAddress() {
		if(shopAddress == null||"".equals(shopAddress)){
			shopAddress = SPUtils.getStringValue("shopAddress");
		}
		return shopAddress;
	}
	public void setShopAddress(String shopAddress) {
		SPUtils.saveStringValue("shopAddress", shopAddress);
		this.shopAddress = shopAddress;
	}
	public String getContactName() {
		if(contactName == null||"".equals(contactName)){
			contactName = SPUtils.getStringValue("contactName");
		}
		return contactName;
	}
	public void setContactName(String contactName) {
		SPUtils.saveStringValue("contactName", contactName);
		this.contactName = contactName;
	}
	public long getShopType() {
		if(shopType == 0){
			shopType = SPUtils.getLongValue("shopType");
		}
		return shopType;
	}
	public void setShopType(long shopType) {
		SPUtils.saveLongValue("shopType", shopType);
		this.shopType = shopType;
	}
	public String isExpress() {
		if(isExpress == null||"".equals(isExpress)){
			isExpress = SPUtils.getStringValue("isExpress");
		}
		return isExpress;
	}
	public void setExpress(String isExpress) {
		SPUtils.saveStringValue("isExpress", isExpress);
		this.isExpress = isExpress;
	}
	public String getYtoCode() {
		if(ytoCode == null||"".equals(ytoCode)){
			ytoCode = SPUtils.getStringValue("ytoCode");
		}
		return ytoCode;
	}
	public void setYtoCode(String ytoCode) {
		SPUtils.saveStringValue("ytoCode", ytoCode);
		this.ytoCode = ytoCode;
	}
	public String getIsRelated() {
		if(isRelated == null||"".equals(isRelated)){
			isRelated = SPUtils.getStringValue("isRelated");
		}
		return isRelated;
	}
	public void setIsRelated(String isRelated) {
		SPUtils.saveStringValue("isRelated", isRelated);
		this.isRelated = isRelated;
	}
	public String getPickupSMS() {
		if(pickupSMS == null||"".equals(pickupSMS)){
			pickupSMS = SPUtils.getStringValue("pickupSMS");
		}
		return pickupSMS;
	}
	public void setPickupSMS(String pickupSMS) {
		SPUtils.saveStringValue("pickupSMS", pickupSMS);
		this.pickupSMS = pickupSMS;
	}
	public String getReminderSMS() {
		if(reminderSMS == null||"".equals(reminderSMS)){
			reminderSMS = SPUtils.getStringValue("reminderSMS");
		}
		return reminderSMS;
	}
	public void setReminderSMS(String reminderSMS) {
		SPUtils.saveStringValue("reminderSMS", reminderSMS);
		this.reminderSMS = reminderSMS;
	}
	public String getProvince() {
		if(province == null||"".equals(province)){
			province = SPUtils.getStringValue("province");
		}
		return province;
	}
	public void setProvince(String province) {
		SPUtils.saveStringValue("province", province);
		this.province = province;
	}
	public String getProvinceCode() {
		if(provinceCode == null||"".equals(provinceCode)){
			provinceCode = SPUtils.getStringValue("provinceCode");
		}
		return provinceCode;
	}
	public void setProvinceCode(String provinceCode) {
		SPUtils.saveStringValue("provinceCode", provinceCode);
		this.provinceCode = provinceCode;
	}
	public String getCity() {
		if(city == null||"".equals(city)){
			city = SPUtils.getStringValue("city");
		}
		return city;
	}
	public void setCity(String city) {
		SPUtils.saveStringValue("city", city);
		this.city = city;
	}
	public String getCityCode() {
		if(cityCode == null||"".equals(cityCode)){
			cityCode = SPUtils.getStringValue("cityCode");
		}
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		SPUtils.saveStringValue("cityCode", cityCode);
		this.cityCode = cityCode;
	}
}
